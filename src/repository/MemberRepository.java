package repository;

import models.Member;

import java.util.List;

public interface MemberRepository {
    String generateId();

    void save(Member member);

    Member findById(String id);

    Member findByName(String name);

    Member findByEmail(String email);

    List<Member> findAll();

    void delete(String id);
}
