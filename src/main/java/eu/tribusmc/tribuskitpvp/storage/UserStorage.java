package eu.tribusmc.tribuskitpvp.storage;

import eu.tribusmc.tribuskitpvp.db.SQLFactory;
import eu.tribusmc.tribuskitpvp.objects.User;

public class UserStorage extends StorageAbstract<User> {


    public UserStorage(SQLFactory sqlFactory) {
        super(sqlFactory);
    }

    @Override
    public User[] getAll() {
        return new User[0];
    }
}
