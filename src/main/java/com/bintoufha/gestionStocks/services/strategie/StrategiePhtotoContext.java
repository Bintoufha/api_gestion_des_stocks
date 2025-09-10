package com.bintoufha.gestionStocks.services.strategie;

import com.bintoufha.gestionStocks.exception.ErrorCodes;
import com.bintoufha.gestionStocks.exception.InvalidOperationException;
import lombok.Setter;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.UUID;

public class StrategiePhtotoContext {

    private final ApplicationContext applicationContext;
    private Strategie strategie;

    @Autowired
    public StrategiePhtotoContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Object savePhoto(String context, UUID uuid, InputStream photo, String titre){
        determinContext(context);
        return strategie.savePhoto(uuid,photo,titre);
    }

    private void determinContext(String context) {
        final String beanName = context.toLowerCase() + "Strategie";
        switch (context.toLowerCase()){
            case "article":
                strategie = applicationContext.getBean(beanName,SaveArticlePhoto.class);
                break;
            case "client":
                strategie = applicationContext.getBean(beanName,SaveClientPhoto.class);
                break;
            case "fournisseur":
                strategie = applicationContext.getBean(beanName,SaveFournisseurPhoto.class);
                break;
            case "entreprise":
                strategie = applicationContext.getBean(beanName,SaveEntreprisePhoto.class);
                break;
            case "utilisateur":
                strategie = applicationContext.getBean(beanName,SaveUtilisateursPhoto.class);
                break;
            default: throw new InvalidOperationException("Context inconnu pour l'enregistrement des image"
                    , ErrorCodes.UNKNOWN_CONTEXT);
        }
    }
}
