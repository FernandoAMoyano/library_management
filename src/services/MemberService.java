package services;

import models.Member;
import repository.MemberRepository;

import java.util.List;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(String name, String email, String phone) {
        String id = memberRepository.generateId();
        Member member = new Member(id, name, email, phone);
        memberRepository.save(member);
        return member;
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public Member findById(String id) {
        return memberRepository.findById(id);
    }

    public Member findByName(String name) {
        return memberRepository.findByName(name);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public void delete(String id) {
        memberRepository.delete(id);
    }
}
