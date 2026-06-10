import { ChangeDetectorRef, Component, NgZone, OnInit , Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { HasAttributeList, HasPerson, Saveable } from '../../interfaces';
import { ApiPerson, ApiAttribute, AttributeDialogData, SelectItem } from '../../models';
import { MapKeyService, PersonService } from '../../services';
import { AttributeDialogHelper, LifespanUtil } from '../../utils';
import { MainLayoutComponent } from '../../components/main-layout/main-layout.component';
import { MatCard, MatCardTitle, MatCardSubtitle, MatCardContent, MatCardFooter } from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import { AttributeListComponent } from '../../components/attribute-list/attribute-list.component';
import { GoogleMapComponent } from '../../components/google-map/google-map.component';
import { MultimediaGalleryComponent } from '../../components/multimedia-gallery/multimedia-gallery.component';
import { PersonFamilyListComponent } from './person-family-list.component';
import { PersonParentFamiliesComponent } from './person-parent-families.component';

/**
 * Implements a person page.
 *
 * Fetches:
 *  person: the person routed by the module
 */
@Component({
    selector: 'app-person',
    standalone: true,
    template: `<app-main-layout [dataset]="dataset">
  <mat-card>
    <div class="card-header-line custom-main-colors">
      <mat-card-title><mat-icon inline=true>person</mat-icon> {{ person?.indexName }}</mat-card-title>
      <mat-card-subtitle>{{ lifespanDateString() }} : {{ person?.string }}</mat-card-subtitle>
    </div>
    <mat-card-content class="custom-main-colors">
      <div class="ui-g custom-main-colors">
        <div class="ui-g-12 person-summary-row custom-main-colors">
          <div class="attributes-section">
            <app-attribute-list [dataset]="dataset" [parent]="this" [attributes]="attributes"
                    [toggleable]="true"></app-attribute-list>
          </div>
          @if (canRenderMap()) {
            <div class="map-section">
              <app-google-map [places]="mapPlaces" [apiKey]="googleMapsApiKey"></app-google-map>
            </div>
          }
        </div>
        <div class="ui-g-12">
          <app-multimedia-gallery [dataset]="dataset" [parent]="this" [multimedia]="person?.images"></app-multimedia-gallery>
        </div>
        <div class="ui-g-12 family-sections-row">
          <div class="family-section-column">
            <app-person-family-list [dataset]="dataset" [person]="person" [parent]="this"></app-person-family-list>
          </div>
          <div class="family-section-column">
            <app-person-parent-families [dataset]="dataset" [person]="person" [parent]="this"></app-person-parent-families>
          </div>
        </div>
      </div>
    </mat-card-content>
    <mat-card-footer class="custom-main-colors">
      @if (person?.refns[0]) {
        <div><b>{{ person.refns[0].string }}:&nbsp;</b> {{ person.refns[0].tail }}</div>
      }
      @if (person?.changes[0]) {
        <div><b>{{ person?.changes[0]?.string }}:&nbsp;</b> {{ person?.changes[0]?.attributes[0].string }}</div>
      }
    </mat-card-footer>
  </mat-card>
  <br/>
</app-main-layout>`,
    styles: [`
mat-card-footer {
  padding-left: 20px;
  padding-top: 10px;
  padding-bottom: 10px;
}

mat-card-title {
  margin: 0;
  padding: 0;
}

mat-card-title mat-icon {
  margin-right: 10px;
}

mat-card-subtitle {
  align-self: center;
  margin: 0;
  padding: 0;
}

.mat-icon {
    vertical-align: top;
    font-size: 1.25em;
}

.attributes-section {
  margin-top: 10px;
  flex: 1;
  min-width: 0;
}

.person-summary-row {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.map-section {
  margin-top: 10px;
  flex: 1;
  min-width: 0;
}

.family-sections-row {
  gap: 16px;
  display: flex;
  flex-direction: column;
}

.family-section-column {
  width: 100%;
}

@media (min-width: 960px) {
  .person-summary-row {
    flex-direction: row;
    align-items: stretch;
  }

  .map-section {
    max-width: 520px;
  }

  .family-sections-row {
    display: grid;
    grid-template-columns: minmax(0, 1fr) minmax(0, 1fr);
    column-gap: 16px;
  }

  .family-section-column {
    width: auto;
    min-width: 0;
  }
}
    `],
    imports: [MainLayoutComponent, MatCard, MatCardTitle, MatIcon, MatCardSubtitle, MatCardContent, AttributeListComponent, GoogleMapComponent, MultimediaGalleryComponent, PersonFamilyListComponent, PersonParentFamiliesComponent, MatCardFooter]
})
export class PersonComponent implements OnInit, HasAttributeList, HasPerson, Saveable {
  dataset: string;
  person: ApiPerson;
  attributes: Array<ApiAttribute>;
  mapPlaces: Array<any> = [];
  googleMapsApiKey = '';
  attributeDialogHelper: AttributeDialogHelper = new AttributeDialogHelper(this);
  private readonly _options: Array<SelectItem> = [
      { value: 'Adoption', label: 'Adoption' },
      { value: 'Adult Baptism', label: 'Adult Baptism' },
      { value: 'Adult Christening', label: 'Adult Christening' },
      { value: 'Age', label: 'Age' },
      { value: 'Alias', label: 'Alias' },
      { value: 'Ancestor Interest', label: 'Ancestor Interest' },
      { value: 'Ancestors', label: 'Ancestors' },
      { value: 'Associates', label: 'Associates' },
      { value: 'Baptism', label: 'Baptism' },
      { value: 'Baptism LDS', label: 'Baptism LDS' },
      { value: 'Bar Mitzvah', label: 'Bar Mitzvah' },
      { value: 'Bas Mitzvah', label: 'Bas Mitzvah' },
      { value: 'Birth', label: 'Birth' },
      { value: 'Blessing', label: 'Blessing' },
      { value: 'Burial', label: 'Burial' },
      { value: 'Caste', label: 'Caste' },
      { value: 'Census', label: 'Census' },
      { value: 'Children Count', label: 'Children Count' },
      { value: 'Christening', label: 'Christening' },
      { value: 'Confirmation', label: 'Confirmation' },
      { value: 'Confirmation LDS', label: 'Confirmation LDS' },
      { value: 'Cremation', label: 'Cremation' },
      { value: 'Death', label: 'Death' },
      { value: 'Descendant Interest', label: 'Descendant Interest' },
      { value: 'Descendants', label: 'Descendants' },
      { value: 'Education', label: 'Education' },
      { value: 'Email', label: 'Email' },
      { value: 'Emigration', label: 'Emigration' },
      { value: 'Endowment', label: 'Endowment' },
      { value: 'Event', label: 'Event' },
      { value: 'Fact', label: 'Fact' },
      { value: 'Fax', label: 'Fax' },
      { value: 'First Communion', label: 'First Communion' },
      { value: 'Given Name', label: 'Given Name' },
      { value: 'Graduation', label: 'Graduation' },
      { value: 'ID Number', label: 'ID Number' },
      { value: 'Immigration', label: 'Immigration' },
      { value: 'Language', label: 'Language' },
      { value: 'Name', label: 'Name' },
      { value: 'Name Prefix', label: 'Name Prefix' },
      { value: 'Name Suffix', label: 'Name Suffix' },
      { value: 'Nationality', label: 'Nationality' },
      { value: 'Naturalization', label: 'Naturalization' },
      { value: 'Nickname', label: 'Nickname' },
      { value: 'Marriage Count', label: 'Marriage Count' },
      { value: 'Note', label: 'Note' },
      { value: 'Multimedia', label: 'Multimedia' },
      { value: 'Occupation', label: 'Occupation' },
      { value: 'Ordinance', label: 'Ordinance' },
      { value: 'Ordination', label: 'Ordination' },
      { value: 'Pedigree', label: 'Pedigree' },
      { value: 'Phone', label: 'Phone' },
      { value: 'Physical Description', label: 'Physical Description' },
      { value: 'Probate', label: 'Probate' },
      { value: 'Publication', label: 'Publication' },
      { value: 'Quality of Data', label: 'Quality of Data' },
      { value: 'Reference', label: 'Reference' },
      { value: 'Relationship', label: 'Relationship' },
      { value: 'Religion', label: 'Religion' },
      { value: 'Residence', label: 'Residence' },
      { value: 'Restriction', label: 'Restriction' },
      { value: 'Retirement', label: 'Retirement' },
      { value: 'Sex', label: 'Sex' },
      { value: 'Source', label: 'Source' },
      { value: 'Surname Prefix', label: 'Surname Prefix' },
      { value: 'Social Security Number', label: 'Social Security Number' },
      { value: 'Status', label: 'Status' },
      { value: 'Surname', label: 'Surname' },
      { value: 'Text', label: 'Text' },
    ];

  constructor(@Inject(ActivatedRoute) private readonly route: ActivatedRoute,
    @Inject(PersonService) private readonly service: PersonService,
    @Inject(MapKeyService) private readonly mapKeyService: MapKeyService,
    @Inject(Router) private readonly router: Router,
    @Inject(NgZone) private readonly zone: NgZone,
    @Inject(ChangeDetectorRef) private readonly cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.dataset = params['dataset'];
    });
    this.route.data.subscribe(
      (data: {dataset: string, person: ApiPerson}) => {
        // Some link clicks can originate from handlers that execute outside Angular.
        // Re-enter the zone to ensure navigation-driven updates are rendered immediately.
        this.zone.run(() => {
          this.person = data.person;
          this.person.attributes = this.person?.attributes || [];
          this.attributes = this.person.attributes;
          this.mapPlaces = this.normalizePlaces((this.person as any)?.places);
          this.cdr.markForCheck();
        });
      }
    );
    this.mapKeyService.getMapKey().subscribe((key: string) => {
      this.zone.run(() => {
        this.googleMapsApiKey = key || '';
        this.cdr.markForCheck();
      });
    });
  }

  lifespanDateString() {
    return new LifespanUtil(this.person.lifespan).lifespanDateString();
  }

  save() {
    this.service.put(this.dataset, this.person).subscribe(
      (data: ApiPerson) => {
        const personId = data?.string || this.person?.string;
        if (!personId) {
          this.zone.run(() => {
            this.person = data;
            this.person.attributes = this.person?.attributes || [];
            this.attributes = this.person.attributes;
            this.cdr.markForCheck();
          });
          return;
        }

        this.service.getOne(this.dataset, personId).subscribe((fresh: ApiPerson) => {
          this.zone.run(() => {
            this.person = fresh;
            this.person.attributes = this.person?.attributes || [];
            this.attributes = this.person.attributes;
            this.cdr.markForCheck();
          });
        });
      }
    );
  }

  options(): Array<SelectItem> {
    return this._options;
  }

  defaultData(): AttributeDialogData {
    return AttributeDialogHelper.dialogData('Name');
  }

  hasMapPlaces(): boolean {
    return this.mapPlaces.length > 0;
  }

  canRenderMap(): boolean {
    return this.hasMapPlaces() && !!this.googleMapsApiKey;
  }

  private normalizePlaces(places: any): Array<any> {
    if (Array.isArray(places)) {
      return places;
    }
    if (!places) {
      return [];
    }

    // Some payloads can arrive as a single object instead of an array.
    if (this.looksLikePlace(places)) {
      return [places];
    }

    // Defensive support for map/object payloads where place entries are values.
    if (typeof places === 'object') {
      return Object.values(places).filter((item: any) => this.looksLikePlace(item));
    }

    return [];
  }

  private looksLikePlace(place: any): boolean {
    if (!place || typeof place !== 'object') {
      return false;
    }

    const location = place.location;
    return !!location
      || Array.isArray(place.coordinates)
      || Array.isArray(place.southwest)
      || Array.isArray(place.northeast);
  }
}
