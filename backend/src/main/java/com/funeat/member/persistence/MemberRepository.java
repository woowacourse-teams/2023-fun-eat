package com.funeat.member.persistence;

import com.funeat.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPlatformId(final String platformId);
}
