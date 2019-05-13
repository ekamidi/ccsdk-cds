/*
* ============LICENSE_START=======================================================
* ONAP : CDS
* ================================================================================
* Copyright 2019 TechMahindra
*
* Modifications Copyright (C) 2019 IBM
*=================================================================================
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* ============LICENSE_END=========================================================
*/

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ResourceCreationComponent } from './resource-creation.component';

describe('ResourceCreationComponent', () => {
  let component: ResourceCreationComponent;
  let fixture: ComponentFixture<ResourceCreationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ResourceCreationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ResourceCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
