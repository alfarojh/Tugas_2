package com.example.Test.service;


import com.example.Test.model.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    private final List<Member> members;
    private String statusMessage;

    public MemberService() {
        members = new ArrayList<>();
        seedMembers();
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * Mendapatkan indeks baru untuk anggota dengan cara menentukan indeks
     * berikutnya berdasarkan ID anggota terakhir dalam daftar.
     *
     * @return Indeks baru untuk anggota.
     */
    private int getNewIndexMember() {
        if (members.size() == 0) return 1; // Jika daftar kosong, indeks pertama adalah 1.
        return members.get(members.size() - 1).getId() + 1;
    }

    /**
     * Fungsi untuk mendapatkan anggota berdasarkan ID.
     * Mencari anggota dalam daftar anggota berdasarkan ID yang diberikan.
     * Jika anggota dengan ID tersebut ditemukan, fungsi mengembalikan objek Member terkait.
     * Jika tidak ditemukan, fungsi mengembalikan nilai null.
     *
     * @param id ID anggota yang ingin dicari.
     * @return Objek Member yang memiliki ID yang sesuai, atau null jika tidak ditemukan.
     */
    public Member getMemberById(int id) {
        if (getIndexByID(id) < 0) return null;
        else {
            statusMessage = "Member ID found";
            return members.get(getIndexByID(id));
        }
    }

    /**
     * Fungsi untuk mendapatkan jumlah anggota dalam daftar.
     * Mengembalikan jumlah anggota yang saat ini terdaftar dalam daftar anggota.
     *
     * @return Jumlah anggota dalam daftar.
     */
    public int getMemberSize() {
        return members.size();
    }

    /**
     * Fungsi untuk menginisialisasi data awal anggota (seed data).
     * Menambahkan beberapa anggota awal ke dalam daftar anggota dengan menggunakan data yang sudah ditentukan.
     * Setiap anggota baru diberikan ID yang dihitung dari fungsi getNewIndexMember.
     */
    private void seedMembers() {
        members.add(new Member(getNewIndexMember(), "Budi", "Jakarta", "08231383934"));
        members.add(new Member(getNewIndexMember(), "Santi", "Medan", "081325648565"));
        members.add(new Member(getNewIndexMember(), "Dodi", "Surabaya", "08565548565"));
    }

    //=============================================== CRUD ============================================

    /**
     * Fungsi untuk mendapatkan daftar anggota yang aktif.
     * Membuat dan mengembalikan daftar anggota yang memiliki status aktif.
     * Anggota yang memiliki status tidak aktif (exist() mengembalikan false) tidak akan dimasukkan ke dalam daftar ini.
     *
     * @return Daftar anggota yang memiliki status aktif.
     */
    public List<Member> getMembersActive() {
        List<Member> memberListActive = new ArrayList<>();
        for (Member member: members) {
            if (member.isExist()) {
                memberListActive.add(member);
            }
        }
        return memberListActive;
    }

    /**
     * Fungsi untuk menambahkan anggota baru ke dalam daftar.
     * Memvalidasi nama, alamat, dan nomor telepon sebelum menambahkan anggota.
     * Jika nama, alamat, dan nomor telepon valid, anggota baru akan ditambahkan ke dalam daftar.
     * Jika penambahan berhasil, fungsi mengembalikan nilai true. Jika tidak, mengembalikan false.
     *
     * @param name Nama anggota yang akan ditambahkan.
     * @param address Alamat anggota yang akan ditambahkan.
     * @param phoneNumber Nomor telepon anggota yang akan ditambahkan.
     * @return true jika anggota berhasil ditambahkan, false jika tidak.
     */
    public boolean addMember(String name, String address, String phoneNumber) {
        if (isNameValid(name) && isAddressValid(address) && isPhoneNumberValid(phoneNumber)) {
            members.add(new Member(getNewIndexMember(), name.trim(), address.trim(), phoneNumber.trim()));
            statusMessage = "Member added successfully.";
            return true;
        }
        return false;
    }

    /**
     * Fungsi untuk mengupdate data anggota berdasarkan ID.
     * Memeriksa apakah ID anggota ditemukan dalam daftar. Jika ditemukan,
     * akan memvalidasi data baru (nama, alamat, nomor telepon) dan mengupdate data anggota.
     * Jika pembaruan berhasil, fungsi mengembalikan nilai true. Jika tidak, mengembalikan false.
     *
     * @param id ID anggota yang akan diupdate.
     * @param name Nama anggota yang baru.
     * @param address Alamat anggota yang baru.
     * @param phoneNumber Nomor telepon anggota yang baru.
     * @return true jika pembaruan berhasil, false jika tidak.
     */
    public boolean updateMemberById(int id, String name, String address, String phoneNumber) {
        if (getIndexByID(id) < 0) {
            statusMessage = "Update failed. The ID `" + id + "` does not exist.";
        }
        else if(isNameValid(name) &&
                isAddressValid(address) &&
                isPhoneNumberValid(phoneNumber)) {
            members.get(getIndexByID(id)).setName(name);
            members.get(getIndexByID(id)).setAddress(address);
            members.get(getIndexByID(id)).setPhoneNumber(phoneNumber);
            statusMessage = "Member with ID `" + id + "` updated successfully.";
            return true;
        }
        return false;
    }

    /**
     * Fungsi untuk menghapus anggota berdasarkan ID.
     * Memeriksa apakah ID anggota ditemukan dalam daftar.
     * Jika ditemukan, akan menghapus anggota,
     * jika tidak akan mengembalikan pesan kesalahan.
     *
     * @param id ID anggota yang akan dihapus.
     * @return true jika penghapusan berhasil, false jika tidak.
     */
    public boolean removeMemberById(int id) {
        if (getIndexByID(id) < 0) {
            statusMessage = "Delete failed. The ID `" + id + "` does not exist.";
            return false;
        } else {
            members.get(getIndexByID(id)).delete();
            statusMessage = "Member with ID `" + id + "` deleted successfully.";
            return true;
        }
    }

    //=============================================== CRUD ============================================

    /**
     * Fungsi untuk memvalidasi alamat anggota.
     * Memeriksa apakah nilai alamat adalah null atau string kosong.
     * Jika null atau kosong, mengembalikan false dan mengatur pesan status kesalahan.
     * Jika tidak, mengembalikan true.
     *
     * @param address Alamat anggota yang akan divalidasi.
     * @return true jika alamat valid, false jika tidak.
     */
    private boolean isAddressValid(String address) {
        if (address == null) {
            statusMessage = "The value of `address` cannot be null!";
            return false;
        } else if (address.trim().equals("")) {
            statusMessage = "The value of `address` cannot be empty!";
            return false;
        } else return true;
    }

    /**
     * Fungsi untuk memvalidasi nama anggota.
     * Memeriksa apakah nilai nama adalah null, string kosong, atau mengandung simbol.
     * Jika null atau kosong, atau mengandung simbol, mengembalikan false dan mengatur pesan status kesalahan.
     * Jika tidak, mengembalikan true.
     *
     * @param name Nama anggota yang akan divalidasi.
     * @return true jika nama valid, false jika tidak.
     */
    private boolean isNameValid(String name) {
        if (name == null) {
            statusMessage = "The value of `name` cannot be null!";
            return false;
        } else if (name.trim().equals("")) {
            statusMessage = "The value of `name` cannot be empty!";
            return false;
        } else if (!name.matches("[a-zA-Z0-9\\s]+")) {
            statusMessage = "The value of `name` cannot contain symbols!";
            return false;
        } else return true;
    }

    /**
     * Fungsi untuk memvalidasi nomor telepon anggota.
     * Memeriksa apakah nilai nomor telepon adalah null, string kosong, atau tidak berupa angka.
     * Jika null, kosong, atau bukan angka, mengembalikan false dan mengatur pesan status kesalahan.
     * Jika tidak, memeriksa juga panjang nomor telepon.
     * Jika tidak sesuai rentang 10-13 digit, mengembalikan false
     * dan mengatur pesan status kesalahan. Jika valid, mengembalikan true.
     *
     * @param phoneNumber Nomor telepon anggota yang akan divalidasi.
     * @return true jika nomor telepon valid, false jika tidak.
     */
    private boolean isPhoneNumberValid(String phoneNumber) {
        if (phoneNumber == null) {
            statusMessage = "The value of `phoneNumber` cannot be null!";
            return false;
        } else if (phoneNumber.trim().equals("")) {
            statusMessage = "The value of `phoneNumber` cannot be empty!";
            return false;
        } else if (!phoneNumber.matches("\\d+")) {
            statusMessage = "The value of `phoneNumber` must be a number.";
            return false;
        } else if (phoneNumber.length() < 10 || phoneNumber.length() > 13) {
            statusMessage = "The value of `phoneNumber` must be a number between 10 and 13.";
            return false;
        } else return true;
    }


    public int getIndexByID(int id) {
        if (id > 0 && id <= members.size()) {
            if (members.get(id - 1).isExist()) {
                return id - 1;
            }
        }
        statusMessage = "Member ID not found";
        return -1;
    }
}
