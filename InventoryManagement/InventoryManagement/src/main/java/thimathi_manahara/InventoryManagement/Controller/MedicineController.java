package thimathi_manahara.InventoryManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thimathi_manahara.InventoryManagement.Data.MedicineDetails;
import thimathi_manahara.InventoryManagement.Service.MedicineService;

import java.util.List;

@RestController
public class MedicineController {

    @Autowired
    MedicineService medicineService;

    @GetMapping(path = "/medicines")
    public List<MedicineDetails> getMineDetails() {
        return medicineService.getAllMedicineDetails();
    }

    @GetMapping(path = "/medicines/{medicineId}")
    public MedicineDetails getMedicineDetailsById(@PathVariable int medicineId) {
        return medicineService.getMedicineDetailsById(medicineId);
    }

    @PostMapping(path = "/medicines")
    public void addMedicine(@RequestBody MedicineDetails medicineDetails) {
        medicineService.addMedicineDetails(medicineDetails);
    }

    @PutMapping(path = "/medicines")
    public MedicineDetails updateMedicine(@RequestBody MedicineDetails medicineDetails) {
       return medicineService.updateMedicine(medicineDetails);
    }

    @PutMapping(path = "/medicines/my-medicine")
    public MedicineDetails updateMedicineDetails(@RequestBody MedicineDetails medicineDetails) {
        return medicineService.updateMedicineDetails(medicineDetails);
    }


    @DeleteMapping(path = "/medicines/{medicineId}")
    public void deleteMedicine(@PathVariable Integer medicineId) {
        if(medicineId != null){
            medicineService.deleteMedicineDetails(medicineId);
        }
        else {
            System.out.println("Medicine id is null");
        }
    }
}