package com.will;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class Block {

    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
    public long timeStamp; //as number of milliseconds since 1/1/1970.
    public int nonce;

    public Block(String previousHash) {

        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash(); //Making sure we do this after we set the values.
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot);
        return calculatedHash;
    }

    //Increases nonce value until hash target is reached.
    public void mineBlock(int difficulty) {
        String target = StringUtil.getDifficultyString(difficulty); ; //Create a string with difficulty * "0"
        while(!hash.substring(0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!! : " + hash);
    }

    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }



  /*  private String[] transactions;
    private int blockHash;
    private int previousBlockHash;

    @Override
    public String toString() {
        return "Block{" +
                "transactions=" + Arrays.toString(transactions) +
                ", blockHash=" + blockHash +
                ", previousBlockHash=" + previousBlockHash +
                '}';
    }

    public Block(String[] transactions, int previousBlockHash) {
        this.transactions = transactions;
        this.previousBlockHash = previousBlockHash;
        this.blockHash = Arrays.hashCode( new int[] { Arrays.hashCode(transactions), this.previousBlockHash});
    }

    public String[] getTransactions() {
        return transactions;
    }

    public void setTransactions(String[] transactions) {
        this.transactions = transactions;
    }

    public int getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(int blockHash) {
        this.blockHash = blockHash;
    }

    public int getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(int previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }*/
}
