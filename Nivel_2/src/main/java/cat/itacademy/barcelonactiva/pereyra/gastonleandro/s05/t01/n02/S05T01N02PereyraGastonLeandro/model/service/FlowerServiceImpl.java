package cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n02.S05T01N02PereyraGastonLeandro.model.service;

import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n02.S05T01N02PereyraGastonLeandro.exception.FlowerServiceException;
import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n02.S05T01N02PereyraGastonLeandro.model.domain.FlowerEntity;
import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n02.S05T01N02PereyraGastonLeandro.model.dto.FlowerDTO;
import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n02.S05T01N02PereyraGastonLeandro.model.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlowerServiceImpl implements FlowerService {

    @Autowired
    private FlowerRepository flowerRepository;

    @Override
    public List<FlowerDTO> addFlowers(List<FlowerDTO> flowerDTOs) {
        List<FlowerEntity> flowers = flowerDTOs.stream().map(dto -> {
            FlowerEntity flower = new FlowerEntity();
            flower.setFlowerName(dto.getFlowerName());
            flower.setFlowerCountry(dto.getFlowerCountry());

            return flower;

        }).collect(Collectors.toList());

        List<FlowerEntity> savedFlowers = flowerRepository.saveAll(flowers);

        return savedFlowers.stream().map(flower -> new FlowerDTO(
                flower.getPk_FlowerID(),
                flower.getFlowerName(),
                flower.getFlowerCountry()

        )).collect(Collectors.toList());
    }

    @Override
    public FlowerDTO updateFlower(Integer id, FlowerDTO flowerDTO) {
        Optional<FlowerEntity> existingFlower = flowerRepository.findById(id);

        if (existingFlower.isEmpty()) throw new FlowerServiceException("Flower not found");

        FlowerEntity flower = existingFlower.get();
        flower.setFlowerName(flowerDTO.getFlowerName());
        flower.setFlowerCountry(flowerDTO.getFlowerCountry());
        FlowerEntity updatedFlower = flowerRepository.save(flower);

        return new FlowerDTO(
                updatedFlower.getPk_FlowerID(),
                updatedFlower.getFlowerName(),
                updatedFlower.getFlowerCountry()
        );
    }

    @Override
    public boolean deleteFlower(Integer id) {
        if (!flowerRepository.existsById(id)) throw new FlowerServiceException("Flower not found");

        flowerRepository.deleteById(id);
        return true;
    }

    @Override
    public FlowerDTO getFlowerById(Integer id) {
        if (!flowerRepository.existsById(id)) throw new FlowerServiceException("Flower not found");

        Optional<FlowerEntity> flower = flowerRepository.findById(id);

        return flower.map(value -> new FlowerDTO(
                value.getPk_FlowerID(),
                value.getFlowerName(),
                value.getFlowerCountry()
        )).orElse(null);
    }

    @Override
    public List<FlowerDTO> getAllFlowers() {
        return flowerRepository.findAll().stream()
                .map(flower -> new FlowerDTO(
                        flower.getPk_FlowerID(),
                        flower.getFlowerName(),
                        flower.getFlowerCountry()

                ))
                .collect(Collectors.toList());
    }
}
