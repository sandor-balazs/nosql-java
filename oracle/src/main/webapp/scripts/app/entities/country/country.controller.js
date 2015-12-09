'use strict';

angular.module('ontimeApp')
    .controller('CountryController', function ($scope, $state, Country) {

        $scope.countrys = [];
        $scope.loadAll = function() {
            Country.query(function(result) {
               $scope.countrys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.country = {
                code: null,
                name: null,
                id: null
            };
        };
    });
