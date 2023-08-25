package com.example.Test.controller;

import com.example.Test.model.ApiResponse;
import com.example.Test.model.Member;
import com.example.Test.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * Fungsi untuk mendapatkan daftar anggota aktif melalui metode GET.
     * Mengembalikan respons HTTP dengan status OK (200) dan body berupa daftar anggota aktif
     * yang diperoleh dari layanan memberService.
     *
     * @return Respons HTTP dengan daftar anggota aktif dalam body.
     */
    @GetMapping("")
    public ResponseEntity getMembers() {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMembersActive());
    }

    /**
     * Fungsi untuk mendapatkan data anggota berdasarkan ID melalui metode GET.
     * Menerima parameter ID anggota yang akan dicari.
     * Mengembalikan respons HTTP sesuai dengan hasil pencarian data anggota.
     * Jika data anggota ditemukan, mengembalikan respons dengan status OK (200)
     * dan body berisi objek ApiResponse yang berisi pesan sukses dan data anggota yang ditemukan.
     * Jika data anggota tidak ditemukan, mengembalikan respons dengan status BAD_REQUEST (400)
     * dan body berisi pesan error yang diberikan oleh layanan memberService.
     *
     * @param id ID anggota yang akan dicari.
     * @return Respons HTTP dengan status dan body sesuai hasil pencarian anggota.
     */
    @GetMapping("/{id}")
    public ResponseEntity getMemberById(@PathVariable int id) {
        if (memberService.getMemberById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            memberService.getStatusMessage(),
                            memberService.getMemberById(id)
            ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(memberService.getStatusMessage()));
        }
    }

    /**
     * Fungsi untuk menambahkan anggota baru melalui metode POST.
     * Menerima objek Member sebagai input dan mengembalikan respons HTTP.
     * Jika penambahan berhasil, mengembalikan respons dengan status CREATED (201) dan body berisi
     * pesan berhasil serta data anggota baru yang ditambahkan.
     * Jika penambahan gagal, mengembalikan respons dengan status BAD_REQUEST (400) dan body berisi pesan error.
     *
     * @param member Objek anggota yang akan ditambahkan.
     * @return Respons HTTP dengan status dan body sesuai hasil penambahan anggota.
     */
    @PostMapping("")
    public ResponseEntity addMember(@RequestBody Member member) {
        if (memberService.addMember(member.getName(), member.getAddress(), member.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(
                            memberService.getStatusMessage(),
                            memberService.getMemberById(memberService.getMemberSize())
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(memberService.getStatusMessage()));
        }
    }

    /**
     * Fungsi untuk memperbarui data anggota melalui metode PUT.
     * Menerima parameter ID anggota yang akan diperbarui dan objek Member sebagai input.
     * Mengembalikan respons HTTP sesuai dengan hasil pembaruan data anggota.
     * Jika pembaruan berhasil, mengembalikan respons dengan status OK (200) dan body berisi
     * data anggota yang telah diperbarui.
     * Jika pembaruan gagal, mengembalikan respons dengan status BAD_REQUEST (400) dan body berisi pesan error.
     *
     * @param id     ID anggota yang akan diperbarui.
     * @param member Objek anggota yang berisi data yang akan diperbarui.
     * @return Respons HTTP dengan status dan body sesuai hasil pembaruan anggota.
     */
    @PutMapping("/{id}")
    public ResponseEntity updateMember(@PathVariable int id, @RequestBody Member member) {
        if (memberService.updateMemberById(id, member.getName(), member.getAddress(), member.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            memberService.getStatusMessage(),
                            memberService.getMemberById(id)
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(memberService.getStatusMessage()));
        }
    }

    /**
     * Fungsi untuk menghapus data anggota melalui metode DELETE.
     * Menerima parameter ID anggota yang akan dihapus.
     * Mengembalikan respons HTTP sesuai dengan hasil penghapusan data anggota.
     * Jika penghapusan berhasil, mengembalikan respons dengan status OK (200) dan body berisi pesan sukses.
     * Jika penghapusan gagal, mengembalikan respons dengan status BAD_REQUEST (400) dan body berisi pesan error.
     *
     * @param id ID anggota yang akan dihapus.
     * @return Respons HTTP dengan status dan body sesuai hasil penghapusan anggota.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity removeMember(@PathVariable int id) {
        if (memberService.removeMemberById(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(memberService.getStatusMessage()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(memberService.getStatusMessage()));
        }
    }
}
