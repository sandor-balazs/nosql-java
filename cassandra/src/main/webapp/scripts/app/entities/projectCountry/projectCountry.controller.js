'use strict';

angular.module('ontimeApp')
    .controller('ProjectCountryController', function ($scope, $state, ProjectCountry) {

        $scope.projectCountrys = [];
        $scope.loadAll = function() {
            ProjectCountry.query(function(result) {
               $scope.projectCountrys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.projectCountry = {
                id: null
            };
        };
    });
