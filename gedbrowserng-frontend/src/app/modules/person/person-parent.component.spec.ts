import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { vi } from 'vitest';

import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { provideNoopAnimations } from '@angular/platform-browser/animations';

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
    imports: [PersonParentComponent],
    providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideNoopAnimations()
    ],
    providers: [PersonService]
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

  it('label returns Father for husband', () => {
    component.attribute = { ...mockAttribute, type: 'husband' };
    expect(component.label()).toBe('Father');
  });

  it('label returns Mother for wife', () => {
    component.attribute = { ...mockAttribute, type: 'wife' };
    expect(component.label()).toBe('Mother');
  });

  it('label returns Parent for other types', () => {
    component.attribute = { ...mockAttribute, type: 'other' };
    expect(component.label()).toBe('Parent');
  });

  it('familyString delegates to parent', () => {
    expect(component.familyString()).toBe('F123');
  });

  it('refreshPerson delegates to parent', () => {
    const spy = vi.spyOn(mockParent, 'refreshPerson');
    component.refreshPerson();
    expect(spy).toHaveBeenCalled();
  });

  it('unlink calls deleteLink and refreshes', () => {
    const person = { string: 'P1' } as any;
    component.person = person;
    const spy = vi.spyOn(TestBed.inject(PersonService), 'deleteLink').mockReturnValue(of(person));
    const refreshSpy = vi.spyOn(mockParent, 'refreshPerson');
    component.unlink();
    expect(spy).toHaveBeenCalled();
    expect(refreshSpy).toHaveBeenCalled();
  });

  it('ngOnChanges re-initializes with current dataset and attribute', () => {
    const initSpy = vi.spyOn(component as any, 'init').mockImplementation(() => {});
    component.dataset = 'ds';
    component.attribute = { ...mockAttribute, string: 'I999' } as any;
    component.ngOnChanges();
    expect(initSpy).toHaveBeenCalledWith('ds', 'I999');
  });
});
