package cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.model.service.branch;

import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.exception.BranchServiceException;
import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.model.domain.Branch;
import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.model.dto.BranchDTO;
import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.model.repository.IBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private IBranchRepository branchRepository;

    @Override
    public void addBranch(BranchDTO branchDTO) {
        Branch branch = new Branch();
        branch.setBranchName(branchDTO.getBranchName());
        branch.setBranchCountry(branchDTO.getBranchCountry());

        branchRepository.save(branch);
    }

    @Override
    public void updateBranch(BranchDTO branchDTO) {
        Optional<Branch> existingBranch = branchRepository.findById(branchDTO.getPk_BranchID());

        if (existingBranch.isEmpty()) throw new BranchServiceException("Branch not found");

        Branch branch = existingBranch.get();
        branch.setBranchName(branchDTO.getBranchName());
        branch.setBranchCountry(branchDTO.getBranchCountry());

        branchRepository.save(branch);
    }

    @Override
    public void deleteBranch(Integer id) {
        if (!branchRepository.existsById(id)) throw new BranchServiceException("Branch not found");

        branchRepository.deleteById(id);
    }

    @Override
    public BranchDTO getBranchById(Integer id) {
        if (!branchRepository.existsById(id)) throw new BranchServiceException("Branch not found");

        Optional<Branch> branch = branchRepository.findById(id);

        return branch.map(value -> new BranchDTO(
                value.getPk_BranchID(),
                value.getBranchName(),
                value.getBranchCountry()
        )).orElse(null);
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        return branchRepository.findAll().stream()
                .map(branch -> new BranchDTO(
                        branch.getPk_BranchID(),
                        branch.getBranchName(),
                        branch.getBranchCountry()
                ))
                .collect(Collectors.toList());
    }
}
