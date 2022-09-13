package com.eventum.backend.dependencies; /*******************************************************************************
 * Copyright 2019 Greyskies. All Rights Reserved.
 ******************************************************************************/

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class HasId<T extends Number> extends BasicEntity implements com.eventum.backend.extradependencies.HasId<T> {

}
