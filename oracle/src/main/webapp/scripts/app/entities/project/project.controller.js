'use strict';

angular.module('ontimeApp')
    .controller('ProjectController', function ($scope, $state, Project) {

        $scope.projects = [];
        $scope.loadAll = function() {
            Project.query(function(result) {
               $scope.projects = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.project = {
                code: null,
                title: null,
                description: null,
                id: null
            };
        };
    });
