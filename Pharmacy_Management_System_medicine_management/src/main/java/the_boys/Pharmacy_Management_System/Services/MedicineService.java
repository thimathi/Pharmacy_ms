package the_boys.Pharmacy_Management_System.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the_boys.Pharmacy_Management_System.Data.MedicinesDetails;
import the_boys.Pharmacy_Management_System.Data.MedicinesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    MedicinesRepository MedicineRepository;

    public List<MedicinesDetails> getAllMedicines(){
        return MedicineRepository.findAll();
    }

    public MedicinesDetails getMedicineDetails(int MedicineID){
        Optional<MedicinesDetails> Medicine = MedicineRepository.findById(MedicineID);
        if(Medicine.isPresent()){
            return Medicine.get();
        }
        return null;
    }

    public MedicinesDetails addMedicine(MedicinesDetails MedicineDetails){
       return MedicineRepository.save(MedicineDetails);
    }
    public MedicinesDetails updateMedicine(MedicinesDetails MedicineDetails){
        return MedicineRepository.save(MedicineDetails);
    }
    public void deleteMedicine(int MedicineID){
        MedicineRepository.deleteById(MedicineID);
    }
}
