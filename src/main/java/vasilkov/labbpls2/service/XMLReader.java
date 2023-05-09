package vasilkov.labbpls2.service;

import nu.xom.*;
import vasilkov.labbpls2.entity.Role;
import vasilkov.labbpls2.entity.User;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class XMLReader {
    public static Optional<User> getByEmailFromXml(String emailQuery) throws ParsingException, IOException {

        File file = new File("./src/main/resources/User.xml");
        Document doc = new Builder().build(file);
        Elements users = doc.getRootElement().getChildElements("user");

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

        if (!exists) return Optional.empty();

        Element user = users.get(personNum);
        Element nameElement = user.getFirstChildElement("name");
        Element firstNameElement = nameElement.getFirstChildElement("first");
        Element lastNameElement = nameElement.getFirstChildElement("last");
        Element passElement = user.getFirstChildElement("password");
        Element emailElement = user.getFirstChildElement("email");
        Element roles = user.getFirstChildElement("roles");
        Element roleUser = roles.getFirstChildElement("user_role");
        Element roleAdmin = roles.getFirstChildElement("admin_role");

        String fName, lName, email, password;
        boolean userRole, adminRole;

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
}
