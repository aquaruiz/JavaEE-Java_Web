package app.service;

import app.domain.entities.User;
import app.domain.models.service.UserServiceModel;
import app.repository.UserRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;

public class UserServiceImpl implements UserService {
    private  final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }
    @Override
    public void save(UserServiceModel user) {
        this.userRepository.save(this.modelMapper.map(user, User.class));
    }

    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {
        return this.modelMapper.map(this.userRepository.findByUserNameAndPassword(username, password), UserServiceModel.class);
    }
}