import {Component, OnInit} from '@angular/core';
import {BlueprintModel} from '../../model/BluePrint.model';
import {PackagesStore} from '../../packages.store';
import {Router} from '@angular/router';
import {ConfigurationDashboardService} from '../../configuration-dashboard/configuration-dashboard.service';
import {saveAs} from 'file-saver';

@Component({
    selector: 'app-packages-list',
    templateUrl: './package-list.component.html',
    styleUrls: ['./package-list.component.css']
})
export class PackageListComponent implements OnInit {

    viewedPackages: BlueprintModel[] = [];


    constructor(private packagesStore: PackagesStore, private router: Router
              , private configurationDashboardService: ConfigurationDashboardService) {
        console.log('PackageListComponent');
        this.packagesStore.state$.subscribe(state => {
            console.log(state);
            if (state.filteredPackages) {
                this.viewedPackages = state.filteredPackages.content;
            }
        });
    }

    ngOnInit() {
        this.packagesStore.getAll();
    }

    view(id) {
        this.router.navigate(['/packages/package', id]);
    }

    testDispatch(bluePrint: BlueprintModel) {
        console.log(bluePrint.id);
    }

    downloadPackage(artifactName: string, artifactVersion: string) {
        this.configurationDashboardService.downloadResource(artifactName + '/' + artifactVersion).subscribe(response => {
            const blob = new Blob([response], {type: 'application/octet-stream'});
            saveAs(blob, artifactName + '-' + artifactVersion + '-CBA.zip');
        });
    }
}
