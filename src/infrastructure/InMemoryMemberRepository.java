package infrastructure;

import models.Member;
import repository.MemberRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryMemberRepository implements MemberRepository {
    private Map<String, Member> members = new HashMap<>();

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Member member) {
        members.put(member.getId(), member);
    }

    @Override
    public Member findById(String id) {
        return members.get(id);
    }

    @Override
    public Member findByName(String name) {
        if (name == null) {
            return null;
        }
        for (Member member : members.values()) {
            if (name.equals(member.getName())) {
                return member;
            }
        }
        return null;
    }

    @Override
    public Member findByEmail(String email) {
        if (email == null) {
            return null;
        }
        for (Member member : members.values()) {
            if (email.equals(member.getEmail())) {
                return member;
            }
        }
        return null;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    @Override
    public void delete(String id) {
        members.remove(id);
    }
}
