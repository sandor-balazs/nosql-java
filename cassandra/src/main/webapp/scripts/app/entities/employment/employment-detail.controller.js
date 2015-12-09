'use strict';

angular.module('ontimeApp')
    .controller('EmploymentDetailController', function ($scope, $rootScope, $stateParams, entity, Employment) {
        $scope.employment = entity;
        $scope.load = function (id) {
            Employment.get({id: id}, function(result) {
                $scope.employment = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:employmentUpdate', function(event, result) {
            $scope.employment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
