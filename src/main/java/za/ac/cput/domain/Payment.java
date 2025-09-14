package za.ac.cput.domain;

/*
Payment.java
Payment POJO class
Author: Bekithemba Mrwetyana (222706066)
Date: 04 May 2025
*/

import jakarta.persistence.*;

import java.time.LocalDate;

import za.ac.cput.domain.enums.PaymentStatus;

@Entity
@Table (name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "paymentID")
    private Long paymentID;

    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name = "orderID", nullable = false)
    private Order order;

    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    protected Payment() {
    }

    public Payment(Builder builder) {
        this.paymentID = builder.paymentID;
        this.paymentDate = builder.paymentDate;
        this.amount = builder.amount;
        this.order = builder.order;
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }

    public static class Builder{

        private Long paymentID;
        private LocalDate paymentDate;
        private double amount;
        private Order order;

        public Builder setPaymentID(Long paymentID) {
            this.paymentID = paymentID;
            return this;
        }

        public Builder setPaymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }
        public Builder setOrder(Order order) {
            this.order = order;
            return this;
        }

        public Builder copy(Payment payment){
            this.paymentID = payment.paymentID;
            this.paymentDate = payment.paymentDate;
            this.amount = payment.amount;
            this.order = payment.order;
            return this;
        }
        public Payment build(){
            return new Payment(this);
        }
    }
}
