package cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.controller;

import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.model.dto.BranchDTO;
import cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.model.service.branch.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/branch")
@Slf4j
public class BranchController {

    @Autowired
    private BranchService branchService;

    @GetMapping("/dashboard")
    public String showIndex(Model model) {
        model.addAttribute("branches", branchService.getAllBranches());
        return "index";
    }

    @GetMapping("/addBranchForm")
    public String showAddBranchForm(Model model) {
        BranchDTO branchDTO = new BranchDTO();
        model.addAttribute("newBranch", branchDTO);

        return "addBranchForm";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        BranchDTO branch = branchService.getBranchById(id);

        model.addAttribute("branchToUpdate", branch);

        return "editBranchForm";
    }

    @PostMapping("/add")
    public String addBranch(@ModelAttribute("newBranch") BranchDTO branchDTO) {
        branchService.addBranch(branchDTO);

        return "redirect:/branch/dashboard";
    }

    @PostMapping("/update")
    public String updateBranch(@ModelAttribute("branchToUpdate") BranchDTO branchDTO) {
        branchService.updateBranch(branchDTO);

        return "redirect:/branch/dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteBranch(@PathVariable String id) {
        branchService.deleteBranch(Integer.valueOf(id));

        return "redirect:/branch/dashboard";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<BranchDTO>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable int id) {
        return ResponseEntity.ok(branchService.getBranchById(id));
    }
}
