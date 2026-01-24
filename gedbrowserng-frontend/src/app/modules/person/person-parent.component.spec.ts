import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

import { PersonParentComponent } from './person-parent.component';
import { PersonService } from '../../services/person.service';
import { ApiAttribute } from '../../models';


describe('PersonParentComponent', () => {
  let component: PersonParentComponent;
  let fixture: ComponentFixture<PersonParentComponent>;

  const mockAttribute: ApiAttribute = {
    string: 'I123',
    type: 'husband',
    tail: '',
    attributes: []
  } as ApiAttribute;

  const mockParent = {
    familyString: () => 'F123',
    refreshPerson: () => {}
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      schemas: [NO_ERRORS_SCHEMA],
      declarations: [ PersonParentComponent ],
      imports: [ HttpClientTestingModule ],
      providers: [ PersonService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonParentComponent);
    component = fixture.componentInstance;
    component.dataset = 'testDataset';
    component.attribute = mockAttribute;
    component.parent = mockParent as any;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
