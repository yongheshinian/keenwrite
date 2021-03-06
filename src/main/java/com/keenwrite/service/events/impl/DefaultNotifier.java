/*
 * Copyright 2020 White Magic Software, Ltd.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.keenwrite.service.events.impl;

import com.keenwrite.service.events.Notification;
import com.keenwrite.service.events.Notifier;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * Provides the ability to notify the user of events that need attention,
 * such as prompting the user to confirm closing when there are unsaved changes.
 */
public final class DefaultNotifier implements Notifier {

  /**
   * Contains all the information that the user needs to know about a problem.
   *
   * @param title   The context for the message.
   * @param message The message content (formatted with the given args).
   * @param args    Parameters for the message content.
   * @return A notification instance, never null.
   */
  @Override
  public Notification createNotification(
      final String title,
      final String message,
      final Object... args ) {
    return new DefaultNotification( title, message, args );
  }

  private Alert createAlertDialog(
      final Window parent,
      final AlertType alertType,
      final Notification message ) {

    final Alert alert = new Alert( alertType );

    alert.setDialogPane( new ButtonOrderPane() );
    alert.setTitle( message.getTitle() );
    alert.setHeaderText( null );
    alert.setContentText( message.getContent() );
    alert.initOwner( parent );

    return alert;
  }

  @Override
  public Alert createConfirmation( final Window parent,
                                   final Notification message ) {
    final Alert alert = createAlertDialog( parent, CONFIRMATION, message );

    alert.getButtonTypes().setAll( YES, NO, CANCEL );

    return alert;
  }

  @Override
  public Alert createError( final Window parent, final Notification message ) {
    return createAlertDialog( parent, ERROR, message );
  }
}
