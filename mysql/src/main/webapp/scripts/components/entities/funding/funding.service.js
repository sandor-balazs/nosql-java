'use strict';

angular.module('ontimeApp')
    .factory('Funding', function ($resource, DateUtils) {
        return $resource('api/fundings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
