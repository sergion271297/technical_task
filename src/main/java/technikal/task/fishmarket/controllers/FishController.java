package technikal.task.fishmarket.controllers;


import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import technikal.task.fishmarket.models.Fish;
import technikal.task.fishmarket.models.FishDto;
import technikal.task.fishmarket.models.ImageFile;
import technikal.task.fishmarket.services.FishRepository;
import technikal.task.fishmarket.services.ImageRepository;

@Controller
@RequestMapping("/fish")
public class FishController {
	
	@Autowired
	private FishRepository repo;
	@Autowired
	private ImageRepository imageRepository;
	
	@GetMapping({"", "/"})
	public String showFishList(Model model) {
		List<Fish> fishlist = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("fishlist", fishlist);
		return "index";
	}
	
	@GetMapping("/create")
	public String showCreatePage(Model model) {
		FishDto fishDto = new FishDto();
		model.addAttribute("fishDto", fishDto);
		return "createFish";
	}
	
	@GetMapping("/delete")
	public String deleteFish(@RequestParam int id) {
		
		try {
			Fish fish = repo.findById(id).get();
			for (ImageFile imageFile  : fish.getImageFiles()){
				Path imagePath = Paths.get("public/images/" + imageFile.getFileName());
				Files.delete(imagePath);
				imageRepository.delete(imageFile);
			}
			repo.delete(fish);
			
		}catch(Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/fish";
	}
	@PostMapping("/create")
	public String addFish(@Valid @ModelAttribute FishDto fishDto, BindingResult result) {
		Date catchDate = new Date();

		Fish fish = new Fish();
		fish.setCatchDate(catchDate);
		fish.setName(fishDto.getName());
		fish.setPrice(fishDto.getPrice());
		repo.save(fish);

		for (MultipartFile image : fishDto.getImageFiles()) {
			String storageFileName = catchDate.getTime() + "_" + image.getOriginalFilename();
			try {
				String uploadDir = "public/images/";
				Path uploadPath = Paths.get(uploadDir);
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try(InputStream inputStream = image.getInputStream()){
					Files.copy(inputStream, Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
					ImageFile imageFile = new ImageFile();
					imageFile.setFileName(storageFileName);
					imageFile.setFish(fish);
					imageRepository.save(imageFile);
				}
			}catch(Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
			}
		}

		return "redirect:/fish";
	}

	@PostMapping("/create_v2")
	public ResponseEntity<HttpStatus> addFishV2(@RequestPart("data") FishDto fishDto, @RequestPart("files") List<MultipartFile> files) {

		Date catchDate = new Date();

		Fish fish = new Fish();
		fish.setCatchDate(catchDate);
		fish.setName(fishDto.getName());
		fish.setPrice(fishDto.getPrice());
		repo.save(fish);

		for (MultipartFile image : files) {
			String storageFileName = catchDate.getTime() + "_" + image.getOriginalFilename();
			try {
				String uploadDir = "public/images/";
				Path uploadPath = Paths.get(uploadDir);
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				try(InputStream inputStream = image.getInputStream()){
					Files.copy(inputStream, Paths.get(uploadDir+storageFileName), StandardCopyOption.REPLACE_EXISTING);
					ImageFile imageFile = new ImageFile();
					imageFile.setFileName(storageFileName);
					imageFile.setFish(fish);
					imageRepository.save(imageFile);
				}
			}catch(Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
			}
		}

		return ResponseEntity.ok(HttpStatus.OK);
	}

}
