package the_boys.Pharmacy_Management_System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import the_boys.Pharmacy_Management_System.Data.MedicinesDetails;
import the_boys.Pharmacy_Management_System.Services.MedicineService;

import java.util.List;

@RestController
public class MedicineController {

    @Autowired
    MedicineService medicineService;

    @GetMapping(path = "/medicines")
    public List<MedicinesDetails> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping(path = "/medicines/{medicineId}")
    public MedicinesDetails getMedicineById(@PathVariable int medicineId) {
        return medicineService.getMedicineDetails(medicineId);
    }

    @PostMapping(path = "/medicines")
    public MedicinesDetails addMedicine(@RequestBody MedicinesDetails medicineDetails) {
        return medicineService.addMedicine(medicineDetails);
    }

    @PutMapping(path = "/medicines")
    public MedicinesDetails updateMedicine(@RequestBody MedicinesDetails medicineDetails) {
        return medicineService.updateMedicine(medicineDetails);
    }

    @DeleteMapping(path = "/medicines/{medicineId}")
    public void deleteMedicine(@PathVariable Integer medicineId) {
        if (medicineId != null){
            medicineService.deleteMedicine(medicineId);
        }
        else{
            System.out.println("Medicine id is null");
        }
    }

}
