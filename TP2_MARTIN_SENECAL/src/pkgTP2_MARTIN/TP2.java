/**
 * TP2 de Martin Senécal
 * DA=1737787
 */
package pkgTP2_MARTIN;

import java.util.Scanner;
import java.util.Random;

public class TP2 {

    public static void main(String[] args) {
        //Déclaration des Scanners;
        Scanner entreenb = new Scanner(System.in);//Entrée de nombre
        Scanner entreemot = new Scanner(System.in);//Entrée de caractères(lettres)
        //Déclaration des variables;
        int nbJeux8; //valeur choisi par l'utilisateur pour jeux 8
        int jeuxChoisi/*choix du jeux*/, chiffreAleatoire = 0/*valeur généré aléatoire*/;
        int mise = 0/*argent misé*/, choixJeux2 = 0/*choix utilisateurs entre 2 options*/, portefeuille = 100/*argent initiale*/;
        boolean choixJeuxBool/*choix utilisateurs entre true/false */, choixNbre = true/*choix utilisateurs entre jouer aléatoire ou non*/;
        boolean tabJeu1[] = new boolean[25]; // déclaration du tableau pour le cas #1 soit booleean
        boolean verifierTab = false; //Condition afin de vérifier dans les tableaux si le NbGénéré est présent
        boolean perdant = false, restart = false, continu = false; //Condition afin de déterminer s'il peut rester dans la boucle.
        int longueur = 0;//variable calculant nombre de chiffres entrées pour certains jeux
        int gain; //combien de fois la mise?

        do {  //Commencement du programme; (restart)
            bienvenu(); //appeler la méthode de bienvenue (introduction du jeux)
            choixNbre = choixNombres(choixNbre);//méthode de choix: choisir ses nombres ou choisir aléatoires
            portefeuille = 100; //initialisation du portefeuille à 100$ pour rejouer

            do { //Continuer le jeux; (continu)
                jeuxChoisi = typeJeux();//Méthode du choix de type de jeu

                //Longueur des différents tableaux à créer selon jeu.
                switch (jeuxChoisi) {
                    case 4://#dujeu
                        longueur = 6;//Tableau de 6 numéros. (length)
                        break;
                    case 5:
                        longueur = 4;
                        break;
                    case 6:
                        longueur = 3;
                        break;
                    case 7:
                        longueur = 2;
                        break;
                    default: //Les autres cas n'ont pas besoin de tableaux générés.
                        break;
                }

                int tabNbres[] = new int[longueur]; //initialisation tableau (nombres choisis)
                int tabNbresTri[] = new int[longueur]; //initialisation tableaux (nombres trié).
                verifierTab = false; //initialisation à falsepour valider numéros. 

                do { //boucle qui demande à entrer mise (ne dépasse pas valeurs portefeuille)
                    mise = MiseJoueur(mise);
                } while (mise > portefeuille);

                switch (jeuxChoisi) { //Chaque cas est égale à un jeux choisi et opère les instructions associés.

                    case 1: { //Jeux 1: true ou false (pair ou impair)
                        gain = 2; //gain*mise=total.
                        tabJeu1 = tabJeu1Meth(); //Méthode créant tableau booléeen.
                        //choix de l'utilisateurs entre nb pair ou impair.
                        System.out.println("***************************************************");
                        System.out.println("* Que voulez-vous choisir?                        *"
                                + "\n* true (pair)                                     *"
                                + "\n* false (impair)                                  *");
                        System.out.println("***************************************************");
                        choixJeuxBool = entreemot.nextBoolean();//Valeur du choix.
                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire);//Méthode générant le chiffre aléatoire.

                        //Conditions déterminant nul-perte-gain
                        if (((choixJeuxBool) && (tabJeu1[chiffreAleatoire])) || ((!choixJeuxBool) && (!tabJeu1[chiffreAleatoire]))) { //si le nombre et choix sont égales= gains
                            portefeuille = gagnant(gain, mise, portefeuille); //Méthode calculant le gain d'argent

                        } else if (((choixJeuxBool) && (!tabJeu1[chiffreAleatoire])) || ((!choixJeuxBool) && (tabJeu1[chiffreAleatoire]))) { //si le nombres n'est pas égales aux choix=pertes
                            portefeuille = perdant(mise, portefeuille); //Méthode calculant la perte d'argent.

                        } else {
                            portefeuille = nul(mise, portefeuille); //Méthode qui affiche nul.
                        }
                        break;

                    }

                    case 2: { //Jeux #2: Passe (19à24) ou Manque (1à18)
                        gain = 2; //gain*mise=total.
                        do { //L'utilisateur doit choisir entre Passe ou Manque.
                            System.out.println("***************************************************");
                            System.out.println("* Que voulez-vous choisir?                         *"
                                    + "\n* 1.Passe (19 à 24)                                *"
                                    + "\n* 2.Manque (1 à 18)                                *");
                            System.out.println("***************************************************");
                            choixJeux2 = entreenb.nextInt(); //Valeur du choix.
                        } while (choixJeux2 < 1 || choixJeux2 > 2); //On le refait tant que choix n'est pas choisi.
                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire); //Méthode générant le chiffre aléatoire.

                        //Conditions déterminant le gain/pertes/nul
                        if (chiffreAleatoire == 0) { //Si nb généré=0, c'est nul.
                            portefeuille = nul(mise, portefeuille);//aucun gains/pertes
                        } else if (((choixJeux2 == 1) && (chiffreAleatoire > 18)) || ((choixJeux2 == 2) && (chiffreAleatoire <= 18))) { //Choix et chiffre aléatoire concordent donc gains
                            portefeuille = gagnant(gain, mise, portefeuille); //Méthode qui additionne gains
                        } else if (((choixJeux2 == 1) && (chiffreAleatoire <= 18)) || ((choixJeux2 == 2) && (chiffreAleatoire > 18))) { //Choix et chiffre aléatoire ne concordent pas donc pertes 
                            portefeuille = perdant(mise, portefeuille); //Méthode qui soustrait la mise.
                        }
                        break;
                    }

                    case 3: { //Jeux #3: Douzaine première ou douzaine seconde.
                        gain = 3; //gain*mise=total.
                        do { //demander à l'utilisateur de choisir (entre les 2) sa douzaine
                            System.out.println("***************************************************");
                            System.out.println("* Que voulez-vous choisir?                         *"
                                    + "\n* 1.Douzaine première (1 à 12)                     *"
                                    + "\n* 2.Douzaine deuxième (13 à 24)                    *");
                            System.out.println("***************************************************");
                            choixJeux2 = entreenb.nextInt();
                        } while (choixJeux2 < 1 || choixJeux2 > 2);//vérification du choix

                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire); //Méthode générant le chiffre aléatoire.
                        // Conditions déterminant les gain/pertes/nul
                        if (chiffreAleatoire == 0) { //Si nombre=0, c'est nul.
                            portefeuille = nul(mise, portefeuille);
                        } else if (((choixJeux2 == 1) && (chiffreAleatoire <= 12)) || ((choixJeux2 == 2) && (chiffreAleatoire > 12))) { //Choix et chiffre aléatoire concordent donc gains
                            portefeuille = gagnant(gain, mise, portefeuille); //Méthode qui additionne les gains
                        } else if (((choixJeux2 == 1) && (chiffreAleatoire > 12)) || ((choixJeux2 == 2) && (chiffreAleatoire <= 12))) { //Choix et chiffre aléatoire ne concordent pas donc pertes
                            portefeuille = perdant(mise, portefeuille); //Méthode qui soustrait la mise
                        }
                        break;
                    }

                    case 4: //Jeux #4: Donner 6 numéro et un de ceux-ci doivent être égales au chiffre aléatoire.
                        gain = 6; //gain*mise=total.
                        System.out.println("***************************************************");
                        System.out.println("* Sélection de 6 numéros.                         *");
                        System.out.println("***************************************************");
                        tabNbres = genererTab(tabNbres, choixNbre, longueur); //Méthode qui a générer un tableau de 6 nombres
                        tabNbresTri = triTab(tabNbres); //Méthode:Trier le tableau.
                        afficherTabTri(tabNbresTri); //Méthode:Afficher le tableau trié.
                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire);//Méthode générant le chiffre aléatoire.
                        verifierTab = trouverNbTab(verifierTab, tabNbresTri, chiffreAleatoire); //Méthode (booléen) recherchant le nb généré avec valeur choisi

                        if (verifierTab == true) { //Si nombre est dans le tab, il a gains.
                            portefeuille = gagnant(gain, mise, portefeuille); //Méthode additionnant les gains.
                        } else { //Si nb ne se trouve pas dans tableau alors pertes.
                            portefeuille = perdant(mise, portefeuille);//Méthode enlevant la mise.
                        }
                        break;

                    case 5: //Jeux #5: Donner 4 numéro et un de ceux-ci doivent être égales au chiffre aléatoire.
                        gain = 9;//gain*mise=total.
                        System.out.println("***************************************************");
                        System.out.println("* Sélection de 4 numéros.                         *");
                        System.out.println("***************************************************");
                        tabNbres = genererTab(tabNbres, choixNbre, longueur); //Méthode qui a générer un tableau de 4 nombres 
                        tabNbresTri = triTab(tabNbres); //Méthode:Trier le tableau.
                        afficherTabTri(tabNbresTri); //Méthode:Afficher le tableau trié.
                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire);//Méthode générant le chiffre aléatoire.
                        verifierTab = trouverNbTab(verifierTab, tabNbresTri, chiffreAleatoire);//Méthode (booléen) recherchant le nb généré avec valeur choisi

                        if (verifierTab == true) { //Si nombre est dans le tab, il a gains.
                            portefeuille = gagnant(gain, mise, portefeuille);//Méthode additionnant les gains.
                        } else { //Si nb ne se trouve pas dans tableau alors pertes.
                            portefeuille = perdant(mise, portefeuille);//Méthode enlevant la mise.
                        }
                        break;

                    case 6://Jeux #4: Donner 3 numéro et un de ceux-ci doivent être égales au chiffre aléatoire.
                        gain = 12; //gain*mise=total
                        System.out.println("***************************************************");
                        System.out.println("* Sélection de 3 numéros.                         *");
                        System.out.println("***************************************************");
                        tabNbres = genererTab(tabNbres, choixNbre, longueur); //Méthode qui a générer un tableau de 3 nombres 
                        tabNbresTri = triTab(tabNbres); //Méthode:Trier le tableau.
                        afficherTabTri(tabNbresTri); //Méthode:Afficher le tableau trié.
                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire); //Méthode générant le chiffre aléatoire.
                        verifierTab = trouverNbTab(verifierTab, tabNbresTri, chiffreAleatoire);//Méthode (booléen) recherchant le nb généré avec valeur choisi

                        if (verifierTab == true) { //Si nombre est dans le tab, il a gains.
                            portefeuille = gagnant(gain, mise, portefeuille);//Méthode additionnant les gains.
                        } else { //Si nb ne se trouve pas dans tableau alors pertes.
                            portefeuille = perdant(mise, portefeuille);//Méthode enlevant la mise.
                        }
                        break;

                    case 7://Jeux #4: Donner 2 numéro et un de ceux-ci doivent être égales au chiffre aléatoire.
                        gain = 18;//gain*mise=total
                        System.out.println("***************************************************");
                        System.out.println("* Sélection de 2 numéros.                         *");
                        System.out.println("***************************************************");
                        tabNbres = genererTab(tabNbres, choixNbre, longueur); //Méthode qui a générer un tableau de 2 nombres 
                        tabNbresTri = triTab(tabNbres); //Méthode:Trier le tableau.
                        afficherTabTri(tabNbresTri); //Méthode:Afficher le tableau trié.
                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire); //Méthode générant le chiffre aléatoire.
                        verifierTab = trouverNbTab(verifierTab, tabNbresTri, chiffreAleatoire);//Méthode (booléen) recherchant le nb généré avec valeur choisi

                        if (verifierTab == true) { //Si nombre est dans le tab, il a gains.
                            portefeuille = gagnant(gain, mise, portefeuille);//Méthode additionnant les gains.
                        } else { //Si nb ne se trouve pas dans tableau alors pertes.
                            portefeuille = perdant(mise, portefeuille);//Méthode enlevant la mise.
                        }
                        break;

                    case 8://Jeux #4: Donner 1 numéro et celui-ci doit être égale au chiffre aléatoire.
                        gain = 24; //Gain*mise=total
                        System.out.println("***************************************************");
                        System.out.println("* Sélection de 1 numéro.                          *");
                        System.out.println("***************************************************");
                        if (choixNbre) { //Si usager veut choisir son propre nombre.
                            do {
                                System.out.println("Veuillez entrer un numéro entre 0 et 24 svp.");
                                nbJeux8 = entreenb.nextInt();
                            } while (nbJeux8 < 0 || nbJeux8 > 24);
                        } else { //Si usager veut un nombre généré.
                            System.out.println("Génération d'un nombre aléatoire.");
                            Random r = new Random(); //générer.
                            nbJeux8 = r.nextInt(25); //valeur entre 0 et 24
                        }
                        System.out.println("Votre numéro est: " + nbJeux8);
                        chiffreAleatoire = ChiffreAleatoireMeth(chiffreAleatoire);//Méthode générant le chiffre aléatoire.
                        if (nbJeux8 == chiffreAleatoire) { //Si chiffre pareil alors gains
                            portefeuille = gagnant(gain, mise, portefeuille);//Méthode additionnant gains

                        } else if (nbJeux8 != chiffreAleatoire) { //Si pas égales, pertes
                            portefeuille = perdant(mise, portefeuille); //Méthode enlevant la mise.
                        }
                        break;
                }

                if (portefeuille <= 0) { //Si portefeuille=0$
                    perdant = true; //il est déclaré perdant.

                } else {
                    perdant = false;//si portefeuille>0$, n'est pas perdant.
                }
                if (perdant == false) { //Gagnant à le choix de continuer ou quitter.
                    System.out.println("Voulez-vous remiser?-true(oui) ou false(non)");
                    continu = entreemot.nextBoolean();

                } else { //Perdant ne peux plus continuer sa partie.
                    continu = false;
                }

            } while (continu == true); //Tant que le gagnant veut continuer son jeux

            System.out.println("Voulez-vous rejouer?- true(oui) ou false(non)");
            restart = entreemot.nextBoolean();

        } while (restart == true); //On recommence du début si joueur veut recommencer.
        bye(portefeuille); //Méthode de au revoir en affichant résultats finaux
    }

    /**
     * *************************************************************************************************
     */
    //Début des méthodes secondaires:
    //Méthode #1: inclue les messages de bienvenu
    public static void bienvenu() {
        Scanner entreenb = new Scanner(System.in); //Entrée de nombres
        Scanner entreemot = new Scanner(System.in); //Entrée de caractères (mots)
        int age; //Variable contenant l'âge du joueur.

        //Entrer prénom.
        System.out.println("***************************************************");
        System.out.println("* Bienvenue dans le jeu « Le Rouleau du Hasard »  *"
                + "\n* Veuillez entrer votre prénom:                   *");
        System.out.println("***************************************************");
        entreemot.nextLine();

        //Entrer âge.
        do {
            System.out.println("************************************************");
            System.out.println("* Veuillez entrer votre âge valide:            *");
            System.out.println("************************************************");
            age = entreenb.nextInt();
        } while (age <= 0 || age > 120); //rentrer un âge réaliste.

        //Entrer origine.        
        System.out.println("************************************************");
        System.out.println("* D'où venez-vous?:                            *");
        System.out.println("************************************************");
        entreemot.nextLine(); //rentrer le pays.
        
        //Début du jeux
        System.out.println("***************************************************");
        System.out.println("*  Votre montant de départ est de: 100,00$        *");
        System.out.println("*  Êtes-vous prêt à jouer? Ça commence!           *");
        System.out.println("***************************************************");
    }

//Méthode #2: Afficher les types de jeux et demander choix du jeux.
    public static int typeJeux() {
        int choixUsers; //choix du jeux. (1 à 8)
        Scanner entreenb = new Scanner(System.in); //Entrée de nombres.
        //Afficher les choix  pour l'utilisateur 
        System.out.println("**********************************************");
        System.out.println("*Que voulez-vous choisir comme type de mise?:*"
                + "\n*     -----------------                      *");
        System.out.println("* 1. true ou false (0 non-inclu) - 2x la mise*"
                + "\n* 2.passe ou manque (0 non-inclu)- 2x la mise*"
                + "\n* 3.douzaine (0 non-inclu) - 3x la mise      *"
                + "\n* 4. 6 numéros     - 6x la mise              *"
                + "\n* 5. 4 numéros  - 9x la mise                 *"
                + "\n* 6. 3 numéros - 12x la mise                 *"
                + "\n* 7. 2 numéros  - 18x la mise                *"
                + "\n* 8. Chiffre seul (0 à 24) - 24x la mise!!   *");
        System.out.println("**********************************************");
        do { //Entrer un de ces choix seulement.
            System.out.println("Entrez votre choix valide:");
            choixUsers = entreenb.nextInt();
        } while (choixUsers < 1 || choixUsers > 8);
        return choixUsers; //valeur 'choix' sera retourné aux main afin de faire le switch
    }

    //Méthode #3: Générer 1 nombre aléatoire entre 0 et 24
    public static int ChiffreAleatoireMeth(int ChiffreAleatoireChoisi) {
        Random r = new Random(); //générer.
        ChiffreAleatoireChoisi = r.nextInt(25); //valeur entre 0 et 24
        System.out.println("Le nombre aléatoire choisi par la machine est:" + ChiffreAleatoireChoisi);
        return ChiffreAleatoireChoisi; //Retourner le chiffre aléatoire dans le main.
    }

    //Méthode #4: Demander le nombre d'argent misé.
    public static int MiseJoueur(int mise) {
        Scanner entreenb = new Scanner(System.in); //Entrée de nombres.
        do {
            System.out.println("**********************************************");
            System.out.println("* Combien souhaiter-vous miser?              *");
            System.out.println("**********************************************");
            mise = entreenb.nextInt();
        } while (mise <= 0); //Mise doit être supérieur à 0$
        return mise; //Retourner valeur de 'mise'
    }

    //Méthode #5: Générer une table de valeurs dépendant du jeux (#4à#7)
    public static int[] genererTab(int[] tabNbres /*tableau généré*/, boolean choixNbre /*Valeur déterminant si jeux aléatoire ou choisi.*/, int longueur /*longueur du tableau dépendant du jeux*/) {
        Scanner entreenb = new Scanner(System.in); //Entrée de nombres.
        int nbTableau, nbGen = 0; //*chiffre choisi avant d'être dans tableau*/, nbGen = 0 /*chiffre généré avant d'être dans tableau*/
        boolean continu; //Répéter quand 2 valeurs sont pareil.

        if (choixNbre) { //Si utilisateur veut choisir par lui-même les nombres.
            for (int i = 0; i < longueur; i++) { //création du tableau 
                do {
                    System.out.println("Veuillez entrer le nombre " + (i + 1));
                    nbTableau = entreenb.nextInt(); //on garde en mémoire les nombres.
                    continu = true;
                    tabNbres[i] = 30; //on s'assure que le tableau ne contient aucun nombres au départ pouvant être choisi par l'utilisateur.
                    for (int z = 0; z <= i; z++) {
                        if (tabNbres[z] == nbTableau) {
                            continu = false; //si nb existe déjà, false pour recommencer boucle.
                            System.out.println("Ce nombre est déjà entrer. Ressayez SVP.");
                        }
                    }

                } while (nbTableau < 0 || nbTableau > 24 || continu == false); //on le refais tant que valeurs pas convenables
                tabNbres[i] = nbTableau; //enregistrer la valeur. 

            }

        } else { //Si utilisateur veut chiffre généré aléatoire.
            System.out.println("Génération de nombres aléatoires...");
            for (int i = 0; i < longueur; i++) { //Création du tableau.
                continu = true;
                while (continu) { //tant que true on fais la boucle.
                    Random r = new Random();
                    nbGen = r.nextInt(25); //génération d'un chiffre entre 0 et 24.
                    continu = false; //false donc on ne le refais plus si différent
                    tabNbres[i] = 30;//on s'assure que le tableau ne contient aucun nombres au départ pouvant être choisi par l'utilisateur.
                    for (int z = 0; z <= i; z++) { //on vérifie les valeurs déjà inscrites.
                        if (tabNbres[z] == nbGen) {
                            continu = true; //si valeurs existent déjà donc false alors on refais la boucle.
                        }
                    }
                }
                tabNbres[i] = nbGen; //enregister la valeur
            }
        }

        return tabNbres; //Retour du tableaux rempli dans le main.
    }

    public static int[] triTab(int[] tabNbresTri) { //Méthode servant à trier.
        int temp /*valeur temporaire pour triage*/, min /*valeur min du tableau*/;

        for (int i = 0; i < tabNbresTri.length; i++) { //Triage du tableaux en ordre croissant.
            min = i; //Minimum est établi au nombre à la position 0 

            for (int z = i + 1; z < tabNbresTri.length; z++) { //Vérifier valeurs dans tableaux à date.
                if (tabNbresTri[z] < tabNbresTri[min]) { //Classer les valeurs en ordre croissant.
                    min = z; //Minimum établi à cet position.
                }
            }
            temp = tabNbresTri[min];//Minimum devient temporaire 
            tabNbresTri[min] = tabNbresTri[i]; //Valeur plus petite deveient min.
            tabNbresTri[i] = temp; //échange entre l'autres valeurs et minimum.

        }

        return tabNbresTri;//On retourne la tableau rempli et trié dans le main.

    }

//Méthode #6: Choix utilisateurs à choisir ses nombres ou choisir aléatoire.
    public static boolean choixNombres(boolean choixNbre) {
        Scanner entreemot = new Scanner(System.in); //Entrée de mots.
        System.out.println("***************************************************");
        System.out.println("* Que voulez-vous choisir?                        *"
                + "\n* Choisir vos nombres:  true                      *"
                + "\n* Jouer 100% aléatoire: false                     *");
        System.out.println("***************************************************");
        choixNbre = entreemot.nextBoolean();
        return choixNbre; //Retour valeur du choix soit true/false.
    }

    //Méthode #7: Création tableaux de Booléen pour le jeux 1
    public static boolean[] tabJeu1Meth() {
        boolean[] tabJeu1 = new boolean[25];
        for (int i = 0; i < tabJeu1.length; i++) { //initialiser tableaux 
            if (i % 2 == 0) { //Si pair=true
                tabJeu1[i] = true;
            } else if (i % 2 != 0) { //si impair=false.
                tabJeu1[i] = false;
            }
        }
        return tabJeu1; //Retourner tableau dans main.

    }

    //Méthode #8: Afficher le tableau généré trié.
    public static void afficherTabTri(int[] tabNbresTri) {
        int i = 0;
        System.out.println("La suite de numéro est: ");
        for (i = 0; i < tabNbresTri.length; i++) { //Afficher les 6 valeurs du tableau généré.
            System.out.print(tabNbresTri[i] + " ");
        }
        System.out.println(" ");//Saut de ligne.
    }

    //Méthode #9: Vérifier si valeur choisi est présente dans le tableau.
    public static boolean trouverNbTab(boolean verifierTab, int tabNbresTri[], int chiffreAleatoire) {
        int i = 0; //initialisation du i=0 afin de vérifier dans le tableau.
        while ((verifierTab == false) && (i < tabNbresTri.length)) { //Vérifier les valeurs du tableaux tant que pas égales et dépassent pas longueur

            if (tabNbresTri[i] == chiffreAleatoire) { //Si chiffre pareil alors true

                verifierTab = true; //sort donc de la boucle

            } else if (tabNbresTri[i] != chiffreAleatoire) {

                verifierTab = false; //Reste dans la boucle et vérifie les autres valeurs.
                i++; //augmentation du compteur pour vérifier les autres nombres jusqu'à longueur:max.

            }
        }
        return verifierTab; //Retour de la valeur verifierTab (true-gain ou false-pertes.)
    }

//Méthode #10: Afficher la valeur du portefeuille après gain.
    public static int gagnant(int gain, int mise, int portefeuille) {
        portefeuille = (portefeuille + (mise * gain)); //Augmentation d'argent
        System.out.println("***************************************************");
        System.out.println("* Félicitation! Vous venez de faire un gain!      ");
        System.out.println("* Votre gain était de: " + (gain * mise) + "$.    ");
        System.out.println("* Vous avez donc maintenant:" + portefeuille + "$.");
        System.out.println("* Continuer comme ça!                             ");
        System.out.println("***************************************************");
        return portefeuille; //retourner la valeur $$
    }

    //Méthode #11: Afficher la valeur du portefeuille après pertes..
    public static int perdant(int mise, int portefeuille) {
        portefeuille = (portefeuille - (mise)); //Diminution d'argent
        System.out.println("***************************************************");
        System.out.println("* Zut! Vous venez de perdre votre mise..          ");
        System.out.println("* Votre perte est de: " + (mise) + "$.            ");
        System.out.println("* Vous avez donc maintenant:" + portefeuille + "$.");
        System.out.println("* Vous pouvez faire mieux en ressayant ;)         ");
        System.out.println("***************************************************");
        return portefeuille; //retourner la valeur $$
    }

//Méthode #12: Afficher la valeur du portefeuille après gain-perte nul.
    public static int nul(int mise, int portefeuille) {

        System.out.println("***************************************************");
        System.out.println("* Ouff! Vous n'avez rien perdu ni gagné. (nul)    ");
        System.out.println("* Votre perte est de: 0$.                         ");
        System.out.println("* Vous avez donc maintenant:" + portefeuille + "$. ");
        System.out.println("* Retourner jouer! Ne soyez pas nul ;)            ");
        System.out.println("***************************************************");
        return portefeuille; //retourner la valeur $$
    }

    //Méthode #13: Afficher messages d'au revoir + somme final.
    public static void bye(int portefeuille) {
        System.out.println("*****************************************************");
        System.out.println("* Nonnn :( Vous partez!!                            ");
        System.out.println("* Vous finissez avec:" + portefeuille + "$.         ");
        System.out.println("* Nous espérons vous revoir dans un futur proche!   ");
        System.out.println("* Jeu développé dans le cadre de SIM: Martin Senécal");
        System.out.println("*****************************************************");
    }
}
