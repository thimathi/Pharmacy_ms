package thimathi_manahara.InventoryManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thimathi_manahara.InventoryManagement.Data.MedicineDetails;
import thimathi_manahara.InventoryManagement.Data.MedicineRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    MedicineRepository medicineRepository;

    public List<MedicineDetails> getAllMedicineDetails() {
        return medicineRepository.findAll();
    }

    public MedicineDetails getMedicineDetailsById(int id) {
        Optional<MedicineDetails> medicineDetails = medicineRepository.findById(id);
        if(medicineDetails.isPresent()){
            return medicineDetails.get();
        }
        return null;
    }

    public MedicineDetails addMedicineDetails(MedicineDetails medicineDetails) {
        return medicineRepository.save(medicineDetails);
    }

    public MedicineDetails updateMedicine(MedicineDetails medicineDetails) {
        Optional<MedicineDetails> existingMedicineDetails = medicineRepository.findById(medicineDetails.getId());
        if(existingMedicineDetails.isPresent()){
            MedicineDetails existingMedicineDetail = existingMedicineDetails.get();
            existingMedicineDetail.setName(medicineDetails.getName());
            existingMedicineDetail.setDescription(medicineDetails.getDescription());
            existingMedicineDetail.setPrice(medicineDetails.getPrice());
            return medicineRepository.save(existingMedicineDetail);
        }
        else{
            return null;
        }
    }

    public MedicineDetails updateMedicineDetails(MedicineDetails medicineDetails) {
        Optional<MedicineDetails> existingMedicineDetails = medicineRepository.findById(medicineDetails.getId());
        if(existingMedicineDetails.isPresent()){
            MedicineDetails existingMedicineDetail = existingMedicineDetails.get();
            existingMedicineDetail.setName(medicineDetails.getName());
            existingMedicineDetail.setDescription(medicineDetails.getDescription());
            existingMedicineDetail.setPrice(medicineDetails.getPrice());
            return medicineRepository.save(existingMedicineDetail);
        }
        else{
            return null;
        }
    }

    public void deleteMedicineDetails(Integer id) {
        if(id != null){
            medicineRepository.deleteById(id);
        }
        else {
            System.out.println("Medicine id is null");
        }
    }
}
