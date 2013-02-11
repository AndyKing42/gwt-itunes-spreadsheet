package org.greatlogic.itunes.client.view.interfaces;

import org.greatlogic.itunes.client.IClientFactory;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.IsWidget;

public interface IOrgMaintenanceView extends IsWidget {
//--------------------------------------------------------------------------------------------------
HeaderPanel getTopLevelPanel();
void initialize(final IClientFactory clientFactory);
//--------------------------------------------------------------------------------------------------
}