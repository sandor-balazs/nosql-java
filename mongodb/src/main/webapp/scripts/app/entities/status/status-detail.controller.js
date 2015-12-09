'use strict';

angular.module('ontimeApp')
    .controller('StatusDetailController', function ($scope, $rootScope, $stateParams, entity, Status) {
        $scope.status = entity;
        $scope.load = function (id) {
            Status.get({id: id}, function(result) {
                $scope.status = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:statusUpdate', function(event, result) {
            $scope.status = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
