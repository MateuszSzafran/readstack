package pl.javastart.readstack.domain.api;

import pl.javastart.readstack.domain.user.User;
import pl.javastart.readstack.domain.user.UserDao;
import pl.javastart.readstack.domain.vote.Vote;
import pl.javastart.readstack.domain.vote.VoteDao;

import java.time.LocalDateTime;
import java.util.Optional;

public class DiscoveryVoteService {
    private final VoteDao voteDao = new VoteDao();
    private final UserDao userDao = new UserDao();

    public void addVote(DiscoveryVote discoveryVote){
        Vote vote = mapVote(discoveryVote);
        voteDao.save(vote);
    }

    private Vote mapVote(DiscoveryVote discoveryVote) {
        Integer discoveryId = discoveryVote.getDiscoveryId();
        Integer userId = userDao.findUserByName(discoveryVote.getUsername()).orElseThrow().getId();
        Vote.Type type = Vote.Type.valueOf(discoveryVote.getType());
        LocalDateTime dateVoted = LocalDateTime.now();
        return new Vote(userId, discoveryId, type, dateVoted);
    }
}
