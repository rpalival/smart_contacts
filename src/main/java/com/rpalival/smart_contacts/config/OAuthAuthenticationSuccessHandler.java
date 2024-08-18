package com.rpalival.smart_contacts.config;

import com.rpalival.smart_contacts.entities.Providers;
import com.rpalival.smart_contacts.entities.User;
import com.rpalival.smart_contacts.helpers.AppConstants;
import com.rpalival.smart_contacts.helpers.ConfigHelper;
import com.rpalival.smart_contacts.repositories.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    private final UserRepo userRepo;
    private final ConfigHelper configHelper;

    @Autowired
    public OAuthAuthenticationSuccessHandler(UserRepo userRepo, ConfigHelper configHelper) {
        this.userRepo = userRepo;
        this.configHelper = configHelper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("OAuth authentication successful");

        // identify google or github or other login and accordingly check the attributes

        var oauth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = oauth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);

        var oauthUser = (DefaultOAuth2User)authentication.getPrincipal();
        oauthUser.getAttributes().forEach((key,value)->{
            logger.info("{}:{}", key, value);
        });
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword(configHelper.passwordEncoder().encode(UUID.randomUUID().toString()));


        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProvider(Providers.GOOGLE);
            user.setProviderUserId(oauthUser.getName());
            user.setAbout("This account is created using google");

        }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url").toString();
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProvider(Providers.GITHUB);
            user.setAbout("This account is created using github");


        }else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");
        }

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
            if (user2 == null){
                userRepo.save(user);
                logger.info("User saved: {}", user.getEmail());
            }




//        //before redirecting, we can save the data in the database.
//        DefaultOAuth2User user = (DefaultOAuth2User)authentication.getPrincipal();
//        //
//        //        logger.info(user.getName());
//        //        user.getAttributes().forEach((key,value)->{
//        //            logger.info("{} => {}", key, value);
//        //        });
//        //
//        //        logger.info(user.getAuthorities().toString());
//        String email = user.getAttribute("email").toString();
//        String name=user.getAttribute("name").toString();
//        String picture=user.getAttribute("picture").toString();
//
//        User user1 = new User();
//        user1.setEmail(email);
//        user1.setName(name);
//        user1.setProfilePic(picture);
//        user1.setPassword("password");
//        user1.setUserId(UUID.randomUUID().toString());
//        user1.setProvider(Providers.GOOGLE);
//        user1.setEnabled(true);
//        user1.setEmailVerified(true);
//        user1.setProviderUserId(user.getName());
//        user1.setRoleList(List.of(AppConstants.ROLE_USER));
//        user1.setAbout("This account is created using google.");
//
//        User user2 = userRepo.findByEmail(email).orElse(null);
//        if (user2 == null){
//            userRepo.save(user1);
//            logger.info("User saved: {}", email);
//        }


        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");{
        }
    }
}
