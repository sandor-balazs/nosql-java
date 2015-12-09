'use strict';

angular.module('ontimeApp')
    .factory('ProjectCountryMilestone', function ($resource, DateUtils) {
        return $resource('api/projectCountryMilestones/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.planned = DateUtils.convertDateTimeFromServer(data.planned);
                    data.actual = DateUtils.convertDateTimeFromServer(data.actual);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
