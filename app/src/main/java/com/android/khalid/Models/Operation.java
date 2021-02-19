package com.android.khalid.Models;

public class Operation {
        private long transcNum;
        private String prodName;
        private int prodQte;
        private String dateOperation;
        private double prodPrice;

        public Operation(long transcNum, String prodName, double prodPrice, int prodQte, String dateOperation) {
            this.transcNum = transcNum;
            this.prodName = prodName;
            this.prodPrice = prodPrice;
            this.prodQte = prodQte;
            this.dateOperation = dateOperation;
        }

        public long getTranscNum() {
            return transcNum;
        }

        public void setTranscNum(long transcNum) {
            this.transcNum = transcNum;
        }

        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

        public int getProdQte() {
            return prodQte;
        }

        public void setProdQte(int prodQte) {
            this.prodQte = prodQte;
        }

        public String getDateOperation() {
            return dateOperation;
        }

        public void setDateOperation(String dateOperation) {
            this.dateOperation = dateOperation;
        }
    }

