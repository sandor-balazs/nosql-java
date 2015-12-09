'use strict';

angular.module('ontimeApp')
    .controller('ProjectDetailController', function ($scope, $rootScope, $stateParams, entity, Project, Status, Funding) {
        $scope.project = entity;
        $scope.load = function (id) {
            Project.get({id: id}, function(result) {
                $scope.project = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:projectUpdate', function(event, result) {
            $scope.project = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
