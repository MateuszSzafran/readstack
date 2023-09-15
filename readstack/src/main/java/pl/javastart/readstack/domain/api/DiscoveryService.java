package pl.javastart.readstack.domain.api;

import pl.javastart.readstack.domain.discovery.Discovery;
import pl.javastart.readstack.domain.discovery.DiscoveryDao;
import pl.javastart.readstack.domain.user.UserDao;
import pl.javastart.readstack.domain.vote.VoteDao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DiscoveryService {
    private final DiscoveryDao discoveryDao = new DiscoveryDao();
    private final DiscoveryMapper discoveryMapper = new DiscoveryMapper();

    public List<DiscoveryBasicInfo> findAll() {
        List<Discovery> discoveries = discoveryDao.findAll();
        return discoveries.stream()
                .map(discoveryMapper::fromDaoToDto)
                .collect(Collectors.toList());
    }

    public List<DiscoveryBasicInfo> findByCategory(int categoryId) {
        return discoveryDao.findByCategory(categoryId)
                .stream()
                .map(discoveryMapper::fromDaoToDto)
                .toList();
    }

    public void add(DiscoverySaveRequest discovery) {
        Discovery discoveryToSave = discoveryMapper.fromSaveRequestToDiscovery(discovery);
        discoveryDao.save(discoveryToSave);
    }

    private static class DiscoveryMapper {
        private final UserDao userDao = new UserDao();
        private final VoteDao voteDao = new VoteDao();

        DiscoveryBasicInfo fromDaoToDto(Discovery discovery) {
            Integer id = discovery.getId();
            String title = discovery.getTitle();
            String url = discovery.getUrl();
            String description = discovery.getDescription();
            LocalDateTime dateAdded = discovery.getDateAdded();
            int voteCount = voteDao.countByDiscoveryId(id);
            String author = userDao.findById(discovery.getUserId()).orElseThrow().getUsername();
            return new DiscoveryBasicInfo(id, title, url, description, dateAdded, voteCount, author);
        }

        Discovery fromSaveRequestToDiscovery(DiscoverySaveRequest saveRequest) {
            LocalDateTime dateAdded = LocalDateTime.now();
            String description = saveRequest.getDescription();
            String title = saveRequest.getTitle();
            String url = saveRequest.getUrl();
            Integer categoryId = saveRequest.getCategoryId();
            Integer userId = userDao.findUserByName(saveRequest.getAuthor())
                    .orElseThrow()
                    .getId();
            return new Discovery(title, url, description, dateAdded, categoryId, userId);
        }
    }
}
