-- 3. TABLE DES ALERTES
CREATE TABLE alerte (
    id UUID PRIMARY KEY,
    date DATE,
    lue BOOLEAN,
    status VARCHAR(255),
    type VARCHAR(255),
    animal_id UUID NOT NULL REFERENCES animal(id)

);

--  4. TABLE DES CROISEMENTS
CREATE TABLE croisement (
    id UUID PRIMARY KEY,
    date_croisement DATE NOT NULL,
    date_mise_bas_estimee DATE,
    nb_porcelets INT,
    nom_intervenant VARCHAR(255) NOT NULL,
    nom_truie_externe VARCHAR(255),
    nom_verrat_externe VARCHAR(255),
    observations TEXT,
    statut VARCHAR(50) CHECK (statut IN ('EN_GESTATION', 'MISE_BAS', 'ECHEC')) DEFAULT 'EN_GESTATION',
    telephone_intervenant VARCHAR(255) NOT NULL,
    truie_id UUID NOT NULL REFERENCES animal(id),
    verrat_id UUID NOT NULL REFERENCES animal(id),
    ferme_id UUID NOT NULL REFERENCES ferme(id),
    horodatage TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--  5. TABLE D’HISTORIQUE DE MODIFICATION
CREATE TABLE historique_modification (
    id UUID PRIMARY KEY,
    ancienne_valeur TEXT,
    champ_modifie VARCHAR(100),
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    entite_modifiee VARCHAR(100),
    id_entite UUID,
    nom_intervenant VARCHAR(255),
    telephone_intervenant VARCHAR(255),
    nouvelle_valeur TEXT


);

--  6. TABLE DES REFRESH-TOKEN
CREATE TABLE refresh_token (
    id UUID PRIMARY KEY,
    creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expire boolean ,
    valeur VARCHAR(255)

);

--  15. TABLE DES ROLES
CREATE TABLE role (
      id UUID PRIMARY KEY,
    libelle VARCHAR(255)
);

--  7. TABLE DES UTILISATEURS
CREATE TABLE utilisateur (
    id UUID PRIMARY KEY,
     actif boolean NOT NULL,
     email VARCHAR(255),
     horodatage TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     mot_de_passe VARCHAR(255),
     nom VARCHAR(255),
     prenom VARCHAR(255),
     role VARCHAR(255),
     telephone VARCHAR(255),
     ferme_id UUID NOT NULL REFERENCES ferme(id)

);

--  8. TABLE DES JWT
CREATE TABLE jwt (
id UUID PRIMARY KEY,
desactive boolean,
expire boolean,
valeur VARCHAR(255),
refresh_token_id UUID NOT NULL REFERENCES refresh_token(id),
utilisateur_id UUID NOT NULL REFERENCES utilisateur(id)
);

--  9. TABLE DES NAISSANCES
CREATE TABLE naissance(
id UUID PRIMARY KEY,
date_naissance date,
nb_mort INT,
nb_vivant INT,
nom_intervenant VARCHAR(255),
observations TEXT,
telephone_intervenant VARCHAR(255),
croisement_id UUID NOT NULL REFERENCES croisement(id),
ferme_id UUID NOT NULL REFERENCES ferme(id)
);

--  10. TABLE DES OPERATIONS
CREATE TABLE operation (
    id UUID PRIMARY KEY,
    cout NUMERIC(38,2) NOT NULL,
    date DATE,
    dose   DOUBLE PRECISION NOT NULL,
    horodatage TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nom_intervenant VARCHAR(255),
    observations TEXT,
    produit VARCHAR(255),
    telephone_intervenant VARCHAR(255),
    type VARCHAR(255),
    animal_id UUID NOT NULL REFERENCES animal(id),
    ferme_id UUID NOT NULL REFERENCES ferme(id)
);

--  11. TABLE DES QR CODE
CREATE TABLE qrcode (
    id UUID PRIMARY KEY,
    contenu VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    animal_id UUID NOT NULL REFERENCES animal(id),
    ferme_id UUID NOT NULL REFERENCES ferme(id)

);


--  12. TABLE DES STOCK
CREATE TABLE stock (
    id UUID PRIMARY KEY,
    categorie VARCHAR(255),
    description VARCHAR(255),
    fournisseur VARCHAR(255),
    horodatage DATE,
    nom_intervenant VARCHAR (255),
    produit VARCHAR(255),
    quantite INT,
    telephone_intervenant VARCHAR(255),
    type_mouvement VARCHAR(255),
    ferme_id UUID NOT NULL REFERENCES ferme(id),
    seuil_alerte INT
);

--  13. TABLE DES TRANSACTIONS
CREATE TABLE transaction (
    id UUID PRIMARY KEY,
    categorie VARCHAR(255),
    date DATE,
    description VARCHAR(255),
    montant NUMERIC(38,2),
    nom_intervenant VARCHAR(255),
    telephone_intervenant VARCHAR(255),
    type VARCHAR(255),
     animal_id UUID NOT NULL REFERENCES animal(id),
    ferme_id UUID NOT NULL REFERENCES ferme(id)
);




--  14. TABLE DES VALIDATION
CREATE TABLE validation (
    id UUID PRIMARY KEY,
    activation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    code VARCHAR(255),
    creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiration TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    utilisateur_id UUID NOT NULL REFERENCES utilisateur(id)
);

