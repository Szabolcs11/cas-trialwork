import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {FeatureService} from '../../services/feature.service';
import {Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';
import {PATHS} from '../../core/constans/paths';
import {NavigationComponent} from '../../components/navigation-component/navigation-component';
import {FeatureDataResponse} from '../../core/model/ApiResponseModel';
import {MatIconModule} from '@angular/material/icon';

@Component({
  standalone: true,
  selector: 'app-feature-component',
  imports: [CommonModule, NavigationComponent, MatIconModule],
  templateUrl: './feature-component.html',
  styleUrl: './feature-component.scss',
})
export class FeatureComponent implements OnInit{
  private router = inject(Router);
  private featureId!: number;
  public feature: Observable<FeatureDataResponse> | undefined;

  constructor(private route: ActivatedRoute, private featureService: FeatureService, private snackBar: MatSnackBar, ) {}

  public ngOnInit(): void {
    this.featureId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadFeature();
  }

  private loadFeature(): void {
    this.feature = this.featureService.getFeature(this.featureId.toString());
  }

  public deleteFeature(): void {
    this.featureService.deleteFeature(this.featureId.toString()).subscribe((e) => {
      this.snackBar.open(e.message, "Ok");
      if (e.success) {
        this.router.navigate([PATHS.FEATURES])
      }
    })
  }
}
