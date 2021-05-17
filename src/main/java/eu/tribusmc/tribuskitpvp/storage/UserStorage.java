package eu.tribusmc.tribuskitpvp.storage;

import eu.tribusmc.tribuskitpvp.objects.User;

public class UserStorage extends StorageAbstract<User> {


    @Override
    public User[] getAll() {
        return new User[0];
    }
}
