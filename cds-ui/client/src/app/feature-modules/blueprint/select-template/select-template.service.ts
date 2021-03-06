/*
============LICENSE_START==========================================
===================================================================
Copyright (C) 2019 IBM Intellectual Property. All rights reserved.
===================================================================

Unless otherwise specified, all software contained herein is licensed
under the Apache License, Version 2.0 (the License);
you may not use this software except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
============LICENSE_END============================================
*/

import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SelectTemplateService {
  cbaOption: string;
  private messageSource = new BehaviorSubject('default message');
  currentMessage = this.messageSource.asObservable();

  constructor() { }

  setCbaOption(option: string) {
    this.messageSource.next(option);
  }
  // setCbaOption(option: string) {
  //   this.cbaOption = option;
  //   console.log("CBA option set to"+this.cbaOption+":"+option);
  // }

  // getCbaOption(): Observable<any> {
  //   console.log("CBA option is "+this.cbaOption);
  //   // return of(this.cbaOption);
  //   return this.cbaOption.asObservable(); 
  // }
}
