package services;

import models.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberService {
    private Map<String, Member> members = new HashMap<>();

    public void save(Member member) {
        members.put(member.getId(), member);
    }

    public Member findById(String id) {
        return members.get(id);
    }

    public Member findByName(String name) {
        for (Member member : members.values()) {
            if (member.getName().equals(name)) {
                return member;
            }
        }
        return null;
    }

    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    public void delete(String id) {
        members.remove(id);
    }
}
