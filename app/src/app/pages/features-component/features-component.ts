import {Component, inject, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {EnvironmentModel} from '../../core/model/EnvironmentModel';
import {EnvironmentService} from '../../services/environment.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AsyncPipe} from '@angular/common';
import {FeatureModel} from '../../core/model/FeatureModel';
import {FeatureService} from '../../services/feature.service';
import {NavigationComponent} from '../../components/navigation-component/navigation-component';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {PATHS} from '../../core/constans/paths';
import {MatSlideToggleChange, MatSlideToggleModule} from '@angular/material/slide-toggle';

@Component({
  standalone: true,
  selector: 'app-features-component',
  imports: [AsyncPipe, NavigationComponent, ReactiveFormsModule, MatSlideToggleModule],
  templateUrl: './features-component.html',
  styleUrl: './features-component.scss',
})
export class FeaturesComponent implements OnInit{
  private router = inject(Router);

  public environments: Observable<EnvironmentModel[]> | undefined;
  public features: Observable<FeatureModel[]> | undefined;
  public selectedEnvironment: EnvironmentModel = {id: '1', name: 'production', protected: false};

  public featureForm = new FormGroup({
    identifier: new FormControl('', Validators.required),
    name: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required)
  })

  constructor(private environmentService: EnvironmentService, private featureService: FeatureService, private snackBar: MatSnackBar) {}

  public async ngOnInit(): Promise<void> {
    this.loadEnvironments();
    this.loadFeaturesByEnvironment();
  }

  private loadEnvironments(): void {
    this.environments = this.environmentService.getEnvironments().pipe((e) => e)
  }

  private loadFeaturesByEnvironment(): void {
    this.features = this.featureService.getFeaturesByEnvironment(this.selectedEnvironment.name);
  }

  public selectEnvironment(name: string, environmentId: string): void {
    this.selectedEnvironment = {name: name, id: environmentId, protected: false}
    this.features = this.featureService.getFeaturesByEnvironment(name);
  }

  public onCheckboxChanges(featureId: string, event: MatSlideToggleChange): void {
    if (!this.selectedEnvironment) return;
    const checked = event.checked;
    this.featureService.toggleFeature(this.selectedEnvironment!.id, featureId, checked)
  }

  public createFeature(): void {
    if (this.featureForm.invalid) return;
    const values = this.featureForm.value;
    this.featureService.createFeature(values.identifier!, values.name!, values.description!).subscribe((e) => {
      this.snackBar.open(e.message, "Ok");
      if (e.success) {
        this.loadFeaturesByEnvironment();
        this.featureForm.reset();
      }
    })
  }

  public selectFeature(featureId: string): void {
    this.router.navigate([PATHS.FEATURES, featureId])
  }
}
