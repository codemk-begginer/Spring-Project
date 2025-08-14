package tech.steve.essaie.service;

import tech.steve.essaie.model.Animal;

public interface QrCodeService {
    String genererQrCodePourAnimal(Long animalId, String codeAnimal);

    String genererQrCodePourAnimal(Animal animal);
}
