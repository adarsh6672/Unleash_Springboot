package com.unleash.consultationservice.Chat.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    //private final UserService userService;

   /* @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public ChatUser addUser(
            @Payload ChatUser user
    ) {
        userService.saveUser(user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public ChatUser disconnectUser(
            @Payload ChatUser user
    ) {
        userService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<ChatUser>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }*/
}
