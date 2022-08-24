package com.example.twitter.context;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> holder = new ThreadLocal<>();

    public static void set(UserContext userContext) {
        holder.set(userContext);
    }

    public static UserContext get() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }
}
