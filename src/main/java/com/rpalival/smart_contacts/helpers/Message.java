package com.rpalival.smart_contacts.helpers;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Message {
    private String content;
    private MessageType type=MessageType.blue;
}
