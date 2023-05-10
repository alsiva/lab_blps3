package vasilkov.labbpls2.service;

import nu.xom.*;
import vasilkov.labbpls2.entity.Role;
import vasilkov.labbpls2.entity.User;
import vasilkov.labbpls2.exception.AuthException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class XMLService {
    public static Optional<User> getByEmailFromXml(String emailQuery) throws ParsingException, IOException {

        File file = new File("./src/main/resources/User.xml");
        Document doc = new Builder().build(file);
        Elements users = doc.getRootElement().getChildElements("user");


        Result personInXml = getResult(emailQuery, doc);
        if (!personInXml.exists()) return Optional.empty();

        Element user = users.get(personInXml.personNum());
        Element nameElement = user.getFirstChildElement("name");
        Element firstNameElement = nameElement.getFirstChildElement("first");
        Element lastNameElement = nameElement.getFirstChildElement("last");
        Element passElement = user.getFirstChildElement("password");
        Element emailElement = user.getFirstChildElement("email");
        Element roles = user.getFirstChildElement("roles");
        Element roleUser = roles.getFirstChildElement("user_role");
        Element roleAdmin = roles.getFirstChildElement("admin_role");

        try {
            User userResponse = new User();
            userResponse.setEmail(emailElement.getValue());
            userResponse.setPassword(passElement.getValue());
            userResponse.setFirstName(firstNameElement.getValue());
            userResponse.setLastName(lastNameElement.getValue());
            Set<Role> roleSet = new HashSet<>();
            if (Boolean.parseBoolean(roleUser.getValue()))
                roleSet.add(Role.USER);
            if (Boolean.parseBoolean(roleAdmin.getValue()))
                roleSet.add(Role.ADMIN);
            userResponse.setRoles(roleSet);
            return Optional.of(userResponse);

        } catch (NullPointerException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    private static Result getResult(String emailQuery, Document doc) {
        Nodes nodesEmail = doc.query("//email");
        int personNum = -1;
        boolean exists = false;
        for (Node emailXml : nodesEmail) {
            personNum += 1;
            if (emailXml.getValue().equals(emailQuery)) {
                exists = true;
                break;
            }
        }
        return new Result(personNum, exists);
    }

    private record Result(int personNum, boolean exists) {
    }

    public static void addToXml(User user) throws IOException, ParsingException, AuthException {
        File file = new File("./src/main/resources/User.xml");
        Document doc = new Builder().build(file);
        Element root = doc.getRootElement();

        Result personInXml = getResult(user.getEmail(), doc);
        if (personInXml.exists()) throw new AuthException("Пользователь с таким email уже существует");

        Element userEl = new Element("user");
        Element nameEl = new Element("name");
        Element firstNameEl = new Element("first");
        Element lastNameEl = new Element("last");
        Element passwordEl = new Element("password");
        Element emailEl = new Element("email");
        Element rolesEl = new Element("roles");
        Element userRoleEl = new Element("user_role");
        Element adminRoleEl = new Element("admin_role");

        firstNameEl.appendChild(user.getFirstName());
        lastNameEl.appendChild(user.getLastName());
        nameEl.appendChild(firstNameEl);
        nameEl.appendChild(lastNameEl);

        userRoleEl.appendChild(String.valueOf(user.getRoles().contains(Role.USER)));
        adminRoleEl.appendChild(String.valueOf(user.getRoles().contains(Role.ADMIN)));
        rolesEl.appendChild(userRoleEl);
        rolesEl.appendChild(adminRoleEl);

        passwordEl.appendChild(user.getPassword());
        emailEl.appendChild(user.getEmail());


        userEl.appendChild(nameEl);
        userEl.appendChild(passwordEl);
        userEl.appendChild(emailEl);
        userEl.appendChild(rolesEl);

        root.appendChild(userEl);

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        Serializer serializer = new Serializer(fileOutputStream, "UTF-8");
        serializer.setIndent(4);
        serializer.write(doc);
    }


    public static Optional<List<String>> getAllAdminsEmail() throws ParsingException, IOException {

        File file = new File("./src/main/resources/User.xml");
        Document doc = new Builder().build(file);
        Elements users = doc.getRootElement().getChildElements("user");
        List<String> emails = new ArrayList<>();
        for (Element user : users) {
            Element roles = user.getFirstChildElement("roles");
            Element roleAdmin = roles.getFirstChildElement("admin_role");
            if (!Boolean.parseBoolean(roleAdmin.getValue())) continue;
            Element emailElement = user.getFirstChildElement("email");
            emails.add(emailElement.getValue());
        }
        return Optional.of(emails);
    }

}
