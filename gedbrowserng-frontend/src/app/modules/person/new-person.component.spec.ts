import { describe, it, expect } from 'vitest';
import { of } from 'rxjs';

import { NewPersonComponent } from './new-person.component';
import { NewPersonDialogData } from '../../models';

class StubDialogRef<T> {
  constructor(private readonly value: T | undefined) {}
  afterClosed() { return of(this.value); }
}

class StubDialog {
  result: any;
  open(_: any, __: any) { return new StubDialogRef(this.result); }
}

describe('NewPersonComponent', () => {
  it('emits only when dialog returns a result', () => {
    const dlg = new StubDialog();
    const comp = new NewPersonComponent(dlg as any);
    comp.sex = 'M';
    comp.surname = 'Smith';
    let emitted: NewPersonDialogData | null = null;
    comp.emitOK.subscribe((d) => emitted = d);

    // undefined branch: no emit
    dlg.result = undefined;
    comp.openDialog();
    expect(emitted).toBeNull();

    // defined branch: should emit
    dlg.result = { sex: 'M', name: 'John/Smith/', birthDate: '', birthPlace: '', deathDate: '', deathPlace: '' };
    comp.openDialog();
    expect(emitted).not.toBeNull();
  });
});
import { NO_ERRORS_SCHEMA, ComponentFixture, TestBed } from '@angular/core/testing';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import { provideAnimations } from '@angular/platform-browser/animations';

describe('NewPersonComponent', () => {
  let component: NewPersonComponent;
  let fixture: ComponentFixture<NewPersonComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
    schemas: [NO_ERRORS_SCHEMA],
    imports: [MatButtonModule, MatSelectModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, FormsModule, MatDialogModule, NewPersonComponent],
    providers: [provideAnimations()]
})
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
