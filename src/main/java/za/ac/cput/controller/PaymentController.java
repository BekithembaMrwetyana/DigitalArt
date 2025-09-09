package za.ac.cput.controller;

import za.ac.cput.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.service.IPaymentService;
import za.ac.cput.service.PaymentService;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Payment create(@RequestBody Payment payment){
        return service.create(payment);
    }
    @GetMapping("/read/{paymentID}")
    public Payment read(@PathVariable Long paymentID){
        return service.read(paymentID);
    }
    @PutMapping("/update")
    public Payment update(@RequestBody Payment payment){
        return service.update(payment);
    }
    @DeleteMapping("/delete/{paymentID}")
    public void delete(@PathVariable Long paymentID){
        service.delete(paymentID);
    }
    @GetMapping("/getAll")
    public List<Payment> getAll(){
        return service.getAll();
    }

//    @GetMapping("/user/{userId}")
//    public List<Payment> getPaymentsByUser(@PathVariable Long userId) {
//        return service.getPaymentsByUserId(userId);
//    }


}
