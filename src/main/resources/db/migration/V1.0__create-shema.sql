-- =============================================
-- SCHEMA GESTION FERME PORCINE - PostgreSQL
-- =============================================

-- 1. TABLE DES FERMES
CREATE TABLE ferme (
    id UUID PRIMARY KEY,
    adresse VARCHAR(255),
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modification TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    email VARCHAR(255),
    nom VARCHAR(255),
    telephone VARCHAR(255)
);

--  2. TABLE DES ANIMAUX
CREATE TABLE animal (
    id UUID PRIMARY KEY,
    code VARCHAR(255) UNIQUE NOT NULL,
    date_naissance DATE,
    date_mort DATE,
    generation INT,
    nom VARCHAR(255),
    observations VARCHAR(255),
    qr_code_url TEXT,
    sexe VARCHAR(255),
    statut VARCHAR(50) DEFAULT 'VIVANT', -- ARCHIVE, DECEDE
    verrat_id UUID REFERENCES animal(id),
    truie_id UUID REFERENCES animal(id),
    ferme_id UUID NOT NULL REFERENCES ferme(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);





